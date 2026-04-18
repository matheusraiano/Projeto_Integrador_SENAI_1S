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



function toggleMenu() {
    const nav = document.querySelector('nav');
    const btn = document.getElementById('menuHamburguer');
    nav.classList.toggle('menu-aberto');
    btn.classList.toggle('ativo');
    document.body.style.overflow = nav.classList.contains('menu-aberto') ? 'hidden' : '';
}

document.querySelectorAll('nav a').forEach(link => {
    link.addEventListener('click', () => {
        fecharMenu();
    });
});

// Fecha ao clicar no overlay (fora do painel)
document.querySelector('nav').addEventListener('click', function(e) {
    if (e.target === this) fecharMenu();
});

function fecharMenu() {
    const nav = document.querySelector('nav');
    const btn = document.getElementById('menuHamburguer');
    nav.classList.remove('menu-aberto');
    btn.classList.remove('ativo');
    document.body.style.overflow = '';
}