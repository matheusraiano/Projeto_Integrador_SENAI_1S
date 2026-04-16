function abrirPainel(id) {
    document.querySelectorAll('.drawer').forEach(d => d.classList.remove('aberto'));
    document.getElementById(id).classList.add('aberto');
    document.getElementById('overlay').classList.add('ativo');
    document.body.style.overflow = 'hidden';
}

function fecharPainel() {
    document.querySelectorAll('.drawer').forEach(d => d.classList.remove('aberto'));
    document.getElementById('overlay').classList.remove('ativo');
    document.body.style.overflow = '';
}

document.addEventListener('keydown', e => {
    if (e.key === 'Escape') fecharPainel();
});