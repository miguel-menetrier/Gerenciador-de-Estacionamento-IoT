
document.addEventListener('DOMContentLoaded', function () {


    const calendarioHtml = document.getElementById('calendario');

    const seletorRecurso = document.getElementById('reserva_recurso');


    const calendario = new FullCalendar.Calendar(calendarioHtml, {

        // --- Configurações de Aparência ---
        initialView: 'dayGridMonth',
        locale: 'pt-br',
        buttonText: { today: 'Hoje' },
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
        },

        // Não mostra a hora padrão
        displayEventTime: false,


        events: async function (info, callbackSucesso, callbackErro) {

            try {

                const recursoId = seletorRecurso.value;


                if (!recursoId) {
                    callbackSucesso([]);
                    return;
                }


                const resposta = await fetch(`/reserva/calendario/${recursoId}`);


                if (!resposta.ok) {
                    throw new Error('Falha ao buscar reservas');
                }


                const reservas = await resposta.json();

                // 5. Transforma os dados do Backend em "eventos" para o Calendário.
                const eventosFormatados = reservas.map(function (reserva) {

                    // Pega só a parte "HH:mm" da hora.
                    const horaInicio = reserva.horaInicial.substring(0, 5);
                    const horaFim = reserva.horaFinal.substring(0, 5);

                    // Retorna o objeto que o FullCalendar entende.
                    return {
                        title: `${horaInicio} - ${horaFim} `,
                        start: `${reserva.data}T${reserva.horaInicial}`,
                        end: `${reserva.data}T${reserva.horaFinal}`,
                        color: '#dc3545'
                    };
                });

                // 6. Entrega os eventos formatados para o calendário desenhar.
                callbackSucesso(eventosFormatados);

            } catch (erro) {
                console.error('Erro ao carregar eventos:', erro);
                callbackErro(erro);
            }
        }
    });


    calendario.render();


    seletorRecurso.addEventListener('change', function () {
        calendario.refetchEvents();
    });
});