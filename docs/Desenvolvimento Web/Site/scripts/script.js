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

function toggleMenu() {
    const nav = document.querySelector('nav');
    const btn = document.getElementById('menuHamburguer');
    nav.classList.toggle('menu-aberto');
    btn.classList.toggle('ativo');
    document.body.style.overflow = nav.classList.contains('menu-aberto') ? 'hidden' : '';
}

function fecharMenu() {
    const nav = document.querySelector('nav');
    const btn = document.getElementById('menuHamburguer');
    nav.classList.remove('menu-aberto');
    btn.classList.remove('ativo');
    document.body.style.overflow = '';
}

document.addEventListener('keydown', e => {
    if (e.key === 'Escape') fecharPainel();
});

function carregarHeader() {
    fetch('header.html')
        .then(res => res.text())
        .then(html => {
            document.body.insertAdjacentHTML('afterbegin', html);

            // Marca link ativo
            const paginaAtual = window.location.pathname.split('/').pop();
            document.querySelectorAll('nav a').forEach(link => {
                if (link.getAttribute('href') === paginaAtual) {
                    link.classList.add('ativo');
                }
            });

            // Eventos da nav — ficam aqui dentro pois a nav só existe após o fetch
            document.querySelectorAll('nav a').forEach(link => {
                link.addEventListener('click', () => fecharMenu());
            });

            document.querySelector('nav').addEventListener('click', function(e) {
                if (e.target === this) fecharMenu();
            });
        });
}

carregarHeader();