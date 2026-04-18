//
//DRAWER
//
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
//
//MENU CELULAR
//
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
//
//HEADER LOAD
//
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
//
//ENTRAR CONTA
//
// Máscara de CPF
function mascaraCpf(input) {
    let v = input.value.replace(/\D/g, '');
    v = v.replace(/(\d{3})(\d)/, '$1.$2');
    v = v.replace(/(\d{3})(\d)/, '$1.$2');
    v = v.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    input.value = v;
}

// Mostrar/esconder senha
function toggleSenha(inputId, btn) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
        input.type = 'text';
        btn.textContent = '🙈';
    } else {
        input.type = 'password';
        btn.textContent = '👁';
    }
}

// Trocar de drawer sem fechar
function trocarDrawer(idDestino) {
    document.querySelectorAll('.drawer').forEach(d => d.classList.remove('aberto'));
    document.getElementById(idDestino).classList.add('aberto');
}

// Validação do login
function tentarLogin() {
    const cpf = document.getElementById('loginCpf').value.replace(/\D/g, '');
    const senha = document.getElementById('loginSenha').value;
    let valido = true;

    document.getElementById('erroCpf').textContent = '';
    document.getElementById('erroSenha').textContent = '';
    document.getElementById('erroGeral').textContent = '';

    if (cpf.length !== 11) {
        document.getElementById('erroCpf').textContent = 'CPF inválido.';
        valido = false;
    }

    if (senha.length < 8) {
        document.getElementById('erroSenha').textContent = 'Senha deve ter pelo menos 8 caracteres.';
        valido = false;
    }

    if (!valido) return;

    // Aqui futuramente vai a chamada para o backend Java
    // Por enquanto simula um erro de credenciais
    document.getElementById('erroGeral').textContent = 'CPF ou senha incorretos.';
}
//
//ABRIR CONTA
//
// Máscara de celular
function mascaraCelular(input) {
    let v = input.value.replace(/\D/g, '');
    v = v.replace(/^(\d{2})(\d)/, '($1) $2');
    v = v.replace(/(\d{5})(\d{1,4})$/, '$1-$2');
    input.value = v;
}

// Força da senha
document.addEventListener('input', function(e) {
    if (e.target.id !== 'cadSenha') return;
    const senha = e.target.value;
    const barra = document.getElementById('forcaBarra');
    if (!barra) return;

    let forca = 0;
    if (senha.length >= 8) forca++;
    if (/[A-Z]/.test(senha)) forca++;
    if (/[0-9]/.test(senha)) forca++;
    if (/[^A-Za-z0-9]/.test(senha)) forca++;

    const cores = ['', '#e05252', '#e09452', '#e0d452', '#3ed162'];
    const larguras = ['0%', '25%', '50%', '75%', '100%'];
    barra.style.width = larguras[forca];
    barra.style.backgroundColor = cores[forca];
});

// Avançar etapa
function avancarCadastro(etapa) {
    if (!validarEtapa(etapa)) return;

    document.getElementById('passo' + etapa).style.display = 'none';
    document.getElementById('etapa-dot-' + etapa).classList.remove('ativa');
    document.getElementById('etapa-dot-' + etapa).classList.add('concluida');

    const proxima = etapa + 1;
    const proximoPasso = document.getElementById('passo' + proxima);

    if (proximoPasso) {
        proximoPasso.style.display = 'flex';
        proximoPasso.style.flexDirection = 'column';
        proximoPasso.style.gap = '20px';
        document.getElementById('etapa-dot-' + proxima).classList.add('ativa');
    }

    // Esconde o rodapé na etapa 3
    if (etapa === 2) {
        document.getElementById('divisorLogin').style.display = 'none';
        document.getElementById('btnJaTenho').style.display = 'none';
    }
}

// Voltar etapa
function voltarCadastro(etapa) {
    document.getElementById('passo' + etapa).style.display = 'none';
    document.getElementById('etapa-dot-' + etapa).classList.remove('ativa');

    const anterior = etapa - 1;
    document.getElementById('passo' + anterior).style.display = 'flex';
    document.getElementById('passo' + anterior).style.flexDirection = 'column';
    document.getElementById('passo' + anterior).style.gap = '20px';
    document.getElementById('etapa-dot-' + anterior).classList.remove('concluida');
    document.getElementById('etapa-dot-' + anterior).classList.add('ativa');

    if (etapa === 3) {
        document.getElementById('divisorLogin').style.display = 'block';
        document.getElementById('btnJaTenho').style.display = 'block';
    }
}

// Validar cada etapa
function validarEtapa(etapa) {
    let valido = true;

    if (etapa === 1) {
        const nome = document.getElementById('cadNome').value.trim();
        const cpf = document.getElementById('cadCpf').value.replace(/\D/g, '');
        const nasc = document.getElementById('cadNascimento').value;

        document.getElementById('erroCadNome').textContent = '';
        document.getElementById('erroCadCpf').textContent = '';
        document.getElementById('erroCadNascimento').textContent = '';

        if (nome.length < 3) {
            document.getElementById('erroCadNome').textContent = 'Nome muito curto.';
            valido = false;
        }
        if (cpf.length !== 11) {
            document.getElementById('erroCadCpf').textContent = 'CPF inválido.';
            valido = false;
        }
        if (!nasc) {
            document.getElementById('erroCadNascimento').textContent = 'Informe sua data de nascimento.';
            valido = false;
        } else {
            const idade = calcularIdade(nasc);
            if (idade < 18) {
                document.getElementById('erroCadNascimento').textContent = 'Você precisa ter 18 anos ou mais.';
                valido = false;
            }
        }
    }

    if (etapa === 2) {
        const email = document.getElementById('cadEmail').value.trim();
        const celular = document.getElementById('cadCelular').value.replace(/\D/g, '');

        document.getElementById('erroCadEmail').textContent = '';
        document.getElementById('erroCadCelular').textContent = '';

        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            document.getElementById('erroCadEmail').textContent = 'E-mail inválido.';
            valido = false;
        }
        if (celular.length < 10) {
            document.getElementById('erroCadCelular').textContent = 'Celular inválido.';
            valido = false;
        }
    }

    return valido;
}

// Finalizar cadastro
function finalizarCadastro() {
    const senha = document.getElementById('cadSenha').value;
    const conf = document.getElementById('cadSenhaConf').value;

    document.getElementById('erroCadSenha').textContent = '';
    document.getElementById('erroCadSenhaConf').textContent = '';

    if (senha.length < 8) {
        document.getElementById('erroCadSenha').textContent = 'Senha deve ter no mínimo 8 caracteres.';
        return;
    }
    if (senha !== conf) {
        document.getElementById('erroCadSenhaConf').textContent = 'As senhas não coincidem.';
        return;
    }

    // Aqui futuramente vai a chamada para o backend Java
    document.getElementById('passo3').style.display = 'none';
    document.getElementById('etapa-dot-3').classList.remove('ativa');
    document.getElementById('etapa-dot-3').classList.add('concluida');
    document.getElementById('passoSucesso').style.display = 'flex';
    document.getElementById('passoSucesso').style.flexDirection = 'column';
    document.getElementById('passoSucesso').style.gap = '20px';
}

// Calcular idade
function calcularIdade(dataNasc) {
    const hoje = new Date();
    const nasc = new Date(dataNasc);
    let idade = hoje.getFullYear() - nasc.getFullYear();
    const mes = hoje.getMonth() - nasc.getMonth();
    if (mes < 0 || (mes === 0 && hoje.getDate() < nasc.getDate())) idade--;
    return idade;
}