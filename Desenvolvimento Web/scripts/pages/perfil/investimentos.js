export function inicializarInvestimentos(cleanupFunctions) {
    console.log('Carregando');

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