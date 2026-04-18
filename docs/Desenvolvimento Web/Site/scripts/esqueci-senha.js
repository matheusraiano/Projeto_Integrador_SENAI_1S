//Código de Matheus Raiano
// Código simulado — quando tiver backend Java, vem do servidor
const CODIGO_SIMULADO = '123456';

function avancarEsqueci(etapa) {
    if (!validarEtapaEsqueci(etapa)) return;

    document.getElementById('passo' + etapa).classList.add('oculto');
    document.getElementById('etapa-dot-' + etapa).classList.remove('ativa');
    document.getElementById('etapa-dot-' + etapa).classList.add('concluida');

    const proxima = etapa + 1;
    document.getElementById('passo' + proxima).classList.remove('oculto');
    document.getElementById('etapa-dot-' + proxima).classList.add('ativa');
}

function voltarEsqueci(etapa) {
    document.getElementById('passo' + etapa).classList.add('oculto');
    document.getElementById('etapa-dot-' + etapa).classList.remove('ativa');

    const anterior = etapa - 1;
    document.getElementById('passo' + anterior).classList.remove('oculto');
    document.getElementById('etapa-dot-' + anterior).classList.remove('concluida');
    document.getElementById('etapa-dot-' + anterior).classList.add('ativa');
}

function validarEtapaEsqueci(etapa) {
    if (etapa === 1) {
        const cpf = document.getElementById('esqCpf').value.replace(/\D/g, '');
        document.getElementById('erroEsqCpf').textContent = '';

        if (cpf.length !== 11) {
            document.getElementById('erroEsqCpf').textContent = 'CPF inválido.';
            return false;
        }
        // Aqui futuramente valida se o CPF existe no banco via Java
        return true;
    }

    if (etapa === 2) {
        const digitos = document.querySelectorAll('.codigo-digito');
        const codigo = Array.from(digitos).map(i => i.value).join('');
        document.getElementById('erroEsqCodigo').textContent = '';

        if (codigo.length < 6) {
            document.getElementById('erroEsqCodigo').textContent = 'Digite todos os 6 dígitos.';
            return false;
        }

        if (codigo !== CODIGO_SIMULADO) {
            document.getElementById('erroEsqCodigo').textContent = 'Código incorreto. Tente novamente.';
            document.querySelectorAll('.codigo-digito').forEach(i => {
                i.value = '';
                i.classList.remove('preenchido');
            });
            document.querySelectorAll('.codigo-digito')[0].focus();
            return false;
        }

        return true;
    }

    return true;
}

function finalizarEsqueci() {
    const senha = document.getElementById('esqSenha').value;
    const conf = document.getElementById('esqSenhaConf').value;

    document.getElementById('erroEsqSenha').textContent = '';
    document.getElementById('erroEsqSenhaConf').textContent = '';

    if (senha.length < 8) {
        document.getElementById('erroEsqSenha').textContent = 'Senha deve ter no mínimo 8 caracteres.';
        return;
    }

    if (senha !== conf) {
        document.getElementById('erroEsqSenhaConf').textContent = 'As senhas não coincidem.';
        return;
    }

    // Aqui futuramente envia a nova senha para o backend Java
    document.getElementById('passo3').classList.add('oculto');
    document.getElementById('etapa-dot-3').classList.remove('ativa');
    document.getElementById('etapa-dot-3').classList.add('concluida');
    document.getElementById('passoSucesso').classList.remove('oculto');
}

// Navega automaticamente entre os inputs do código
function avancarDigito(input, index) {
    input.value = input.value.replace(/\D/g, '');

    if (input.value) {
        input.classList.add('preenchido');
        const proximos = document.querySelectorAll('.codigo-digito');
        if (proximos[index + 1]) {
            proximos[index + 1].focus();
        }
    } else {
        input.classList.remove('preenchido');
    }
}

// Força da senha na página de esqueci
document.addEventListener('input', function(e) {
    if (e.target.id !== 'esqSenha') return;
    const senha = e.target.value;
    const barra = document.getElementById('forcaBarraEsq');
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

// Apagar dígito com backspace vai para o campo anterior
document.addEventListener('keydown', function(e) {
    if (!e.target.classList.contains('codigo-digito')) return;
    if (e.key !== 'Backspace') return;

    const digitos = document.querySelectorAll('.codigo-digito');
    const index = Array.from(digitos).indexOf(e.target);

    if (!e.target.value && index > 0) {
        digitos[index - 1].focus();
        digitos[index - 1].value = '';
        digitos[index - 1].classList.remove('preenchido');
    }
});

function reenviarCodigo() {
    // Futuramente chama o backend Java para reenviar o SMS
    alert('Código reenviado! (simulação)');
}