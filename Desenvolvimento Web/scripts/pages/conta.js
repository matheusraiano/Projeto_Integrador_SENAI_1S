//
// ================================
// CONTA
// ================================
//
export function inicializarConta(cleanupFunctions) {
    console.log('Conta carregado');

    const horas = document.querySelector('.horas');

    if (!horas) return;

    atualizarHorario();

    const relogioInterval = setInterval(() => {

        atualizarHorario();
    }, 1000);
    // cleanup
    cleanupFunctions.push(() => {

        clearInterval(relogioInterval);
    });
}
//
// RELÓGIO
//
function atualizarHorario() {
    const horas = document.querySelector('.horas');

    if (!horas) return;

    const data = new Date();

    const diasSemana = [
        'Domingo',
        'Segunda-Feira',
        'Terça-Feira',
        'Quarta-Feira',
        'Quinta-Feira',
        'Sexta-Feira',
        'Sábado'
    ];

    const hora = String(data.getHours()).padStart(2, '0');
    const minuto = String(data.getMinutes()).padStart(2, '0');
    const segundo = String(data.getSeconds()).padStart(2, '0');

    const dia = String(data.getDate()).padStart(2, '0');
    const mes = String(data.getMonth() + 1).padStart(2, '0');

    const ano = data.getFullYear();

    horas.innerHTML = `
        ${hora}:${minuto}:${segundo}
        -
        ${dia}/${mes}/${ano}
        -
        ${diasSemana[data.getDay()]}
    `;
}