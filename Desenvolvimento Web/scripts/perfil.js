//Código de Matheus Raiano
//Navegação
const navItems = document.querySelectorAll('.nav-item');
const conteudo = document.getElementById('conteudo-dinamico');
// função para carregar página
const cache = {};

function carregarPagina(pagina) {
    if (cache[pagina]) {
        conteudo.innerHTML = cache[pagina];
        return;
    }

    conteudo.innerHTML = "<p>Carregando...</p>";
    
    fetch(`../paginas/${pagina}`)
        .then(res => res.text())
        .then(html => {
            cache[pagina] = html;
            conteudo.innerHTML = html;
        });
}
// evento de clique
navItems.forEach(item => {
    item.addEventListener('click', () => {
        
        // ativa visual
        navItems.forEach(i => i.classList.remove('ativo'));
        item.classList.add('ativo');

        // carrega conteúdo
        const pagina = item.dataset.page;
        carregarPagina(pagina);

        // salva estado
        localStorage.setItem('paginaAtual', pagina);
    });
});
// carregar ao iniciar
const paginaSalva = localStorage.getItem('paginaAtual') || 'conta.html';
carregarPagina(paginaSalva);

// sincronizar ativo ao recarregar
navItems.forEach(item => {
    if (item.dataset.page === paginaSalva) {
        item.classList.add('ativo');
    } else {
        item.classList.remove('ativo');
    }
});