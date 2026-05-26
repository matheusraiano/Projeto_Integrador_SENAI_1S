export function inicializarConta(cleanupFunctions) {
    console.log('Carregando');
    atualizarHorario();
    // RELÓGIO — atualiza a cada 1 segundo
    const relogioInterval = setInterval(() => {
        atualizarHorario();
    }, 1000);
    // CLEANUP — para o relógio ao sair da página
    cleanupFunctions.push(() => {
        clearInterval(relogioInterval);
    });
    //
    // Aqui futuramente vão as chamadas ao backend Java:
    // carregarSaldo();
    // carregarTransacoes();
    // carregarFatura();
    // carregarInvestimentos();
    // carregarEmprestimo();
    // carregarNotificacoes();
    //
}
//
// ================================
// RELÓGIO
// ================================
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

    const hora    = String(data.getHours()).padStart(2, '0');
    const minuto  = String(data.getMinutes()).padStart(2, '0');
    const segundo = String(data.getSeconds()).padStart(2, '0');
    const dia     = String(data.getDate()).padStart(2, '0');
    const mes     = String(data.getMonth() + 1).padStart(2, '0');
    const ano     = data.getFullYear();

    horas.innerHTML = `${hora}:${minuto}:${segundo} — ${dia}/${mes}/${ano} — ${diasSemana[data.getDay()]}`;
}
//
// ================================
// OCULTAR SALDO
// ================================
//
let saldoOculto = false;

window.ocultarSaldo = function() {
    const saldo = document.getElementById('contaSaldoH1');
    const btn   = document.getElementById('contaBtnOcultarId');

    if (!saldo) return;

    saldoOculto = !saldoOculto;

    if (saldoOculto) {
        saldo.classList.add('oculto');
        btn.textContent = '🙈';
    } else {
        saldo.classList.remove('oculto');
        btn.textContent = '👁';
    }
}
//
// ================================
// CARREGAR DADOS DA CONTA
// Recebe: { saldo, nr_agencia, nr_conta, tp_conta, ds_status }
// ================================
//
function carregarConta(conta) {
    document.getElementById('contaSaldoH1').textContent      = formatarDinheiro(conta.saldo);
    document.getElementById('contaAgencia').textContent      = conta.nr_agencia;
    document.getElementById('contaNumeroConta').textContent  = conta.nr_conta;
    document.getElementById('contaTipo').textContent         = capitalize(conta.tp_conta);

    const statusSpan = document.getElementById('contaStatusSpan');
    statusSpan.textContent = conta.ds_status === 'ativa' ? '● Conta Ativa' : `● ${capitalize(conta.ds_status)}`;
}
//
// ================================
// CARREGAR RESUMO MENSAL
// Recebe: { totalEntradas, qtdEntradas, totalSaidas, qtdSaidas, totalInvestido, rentabilidade }
// ================================
//
function carregarResumo(resumo) {
    document.getElementById('contaTotalEntradas').textContent = formatarDinheiro(resumo.totalEntradas);
    document.getElementById('contaQtdEntradas').textContent   = `${resumo.qtdEntradas} transações este mês`;

    document.getElementById('contaTotalSaidas').textContent   = formatarDinheiro(resumo.totalSaidas);
    document.getElementById('contaQtdSaidas').textContent     = `${resumo.qtdSaidas} transações este mês`;

    document.getElementById('contaTotalInvestido').textContent = formatarDinheiro(resumo.totalInvestido);
    document.getElementById('contaRentabilidade').textContent  = `Rentabilidade +${resumo.rentabilidade}%`;
}
//
// ================================
// CARREGAR TRANSAÇÕES
// Recebe: array de { tp_transacao, valor, descricao, dt_transacao, ds_status, tipo: 'entrada'|'saida' }
// ================================
//
function carregarTransacoes(transacoes) {
    const lista = document.getElementById('contaListaTransacoesId');

    if (!transacoes || transacoes.length === 0) {
        lista.innerHTML = '<p class="contaVazio">Nenhuma transação encontrada.</p>';
        return;
    }

    lista.innerHTML = transacoes.map(t => `
        <div class="contaTransacaoItem">
            <div class="contaTransacaoIcone contaTipo${capitalize(t.tipo)}">
                ${t.tipo === 'entrada' ? '📥' : '📤'}
            </div>
            <div class="contaTransacaoInfo">
                <h5>${t.descricao}</h5>
                <p>${t.tp_transacao.toUpperCase()} • ${formatarData(t.dt_transacao)}</p>
            </div>
            <div class="contaTransacaoValor">
                <span class="contaValor${capitalize(t.tipo)}">
                    ${t.tipo === 'entrada' ? '+' : '-'} ${formatarDinheiro(t.valor)}
                </span>
                <small class="contaBadge ${t.ds_status}">${capitalize(t.ds_status)}</small>
            </div>
        </div>
    `).join('');
}
//
// ================================
// CARREGAR CARTÃO DE CRÉDITO
// Recebe: { nm_cartao, limite_total, limite_usado }
// ================================
//
function carregarCartao(cartao) {
    const limiteDisponivel = cartao.limite_total - cartao.limite_usado;
    const porcentagemUsada = (cartao.limite_usado / cartao.limite_total) * 100;

    document.getElementById('contaNomeCartao').textContent         = cartao.nm_cartao;
    document.getElementById('contaLimiteUsado').textContent        = formatarDinheiro(cartao.limite_usado);
    document.getElementById('contaLimiteDispCartao').textContent   = formatarDinheiro(limiteDisponivel);
    document.getElementById('contaLimiteDisponivel').textContent   = formatarDinheiro(limiteDisponivel);
    document.getElementById('contaLimiteTotal').textContent        = `De ${formatarDinheiro(cartao.limite_total)} no total`;
    document.getElementById('contaLimiteBarraId').style.width      = `${porcentagemUsada}%`;

    // Troca a imagem do cartão mini conforme o nome
    const nomes = { 'Classic': 'cartao_classic', 'Interlagos': 'cartao_interlagos', 'Mônaco': 'cartao_monaco' };
    const imagem = nomes[cartao.nm_cartao] || 'cartao_classic';
    document.getElementById('contaCartaoMiniId').className = `contaCartaoMini ${cartao.nm_cartao.toLowerCase()}`;
}
//
// ================================
// CARREGAR FATURA
// Recebe: { valor_total, dt_vencimento, ds_status }
// ================================
//
function carregarFatura(fatura) {
    document.getElementById('contaFaturaTotal').textContent      = formatarDinheiro(fatura.valor_total);
    document.getElementById('contaFaturaVencimento').textContent = formatarData(fatura.dt_vencimento);

    const badge = document.getElementById('contaFaturaStatusBadge');
    badge.textContent  = capitalize(fatura.ds_status);
    badge.className    = `contaBadge ${fatura.ds_status === 'paga' ? 'concluida' : 'pendente'}`;
}
//
// ================================
// CARREGAR NOTIFICAÇÕES
// Recebe: array de { titulo, mensagem, tp_notificacao, ds_lida, dt_envio }
// ================================
//
function carregarNotificacoes(notificacoes) {
    const lista   = document.getElementById('contaListaNotifId');
    const badgeQt = document.getElementById('contaQtdNotif');

    const naoLidas = notificacoes.filter(n => n.ds_lida === 0);
    badgeQt.textContent = `${naoLidas.length} novas`;

    if (notificacoes.length === 0) {
        lista.innerHTML = '<p class="contaVazio">Nenhuma notificação.</p>';
        return;
    }

    const icones = { transacao: '💳', fatura: '📋', investimento: '📈', seguranca: '🔐' };

    lista.innerHTML = notificacoes.map(n => `
        <div class="contaNotifItem ${n.ds_lida === 0 ? 'naoLida' : ''}">
            <span>${icones[n.tp_notificacao] || '🔔'}</span>
            <div class="contaNotifTexto">
                <h5>${n.titulo}</h5>
                <p>${n.mensagem}</p>
                <small>${formatarData(n.dt_envio)}</small>
            </div>
        </div>
    `).join('');
}
//
// ================================
// CARREGAR INVESTIMENTOS
// Recebe: array de { tp_investimento, ds_descricao, valor_atual, rentabilidade, dt_vencimento }
// ================================
//
function carregarInvestimentos(investimentos) {
    const lista = document.getElementById('contaListaInvestimentosId');

    if (!investimentos || investimentos.length === 0) {
        lista.innerHTML = '<p class="contaVazio">Nenhum investimento ativo.</p>';
        return;
    }

    lista.innerHTML = investimentos.map(inv => `
        <div class="contaInvestItem">
            <div class="contaInvestItemInfo">
                <h5>${inv.ds_descricao || inv.tp_investimento}</h5>
                <p>${inv.tp_investimento} • Vence ${formatarData(inv.dt_vencimento)}</p>
            </div>
            <div class="contaInvestItemValor">
                <h3>${formatarDinheiro(inv.valor_atual)}</h3>
                <small class="contaValorEntrada">↑ +${inv.rentabilidade}%</small>
            </div>
        </div>
    `).join('');
}
//
// ================================
// CARREGAR EMPRÉSTIMO
// Recebe: { tp_emprestimo, valor_aprovado, taxa_juros, nr_parcelas, ds_status,
//           parcelasPagas, proximaParcela: { valor_parcela, dt_vencimento } }
// ================================
//
function carregarEmprestimo(emprestimo) {
    const porcentagem = (emprestimo.parcelasPagas / emprestimo.nr_parcelas) * 100;

    document.getElementById('contaEmprestimoTipo').textContent          = capitalize(emprestimo.tp_emprestimo);
    document.getElementById('contaEmprestimoValor').textContent         = formatarDinheiro(emprestimo.valor_aprovado);
    document.getElementById('contaEmprestimoTaxa').textContent          = `${emprestimo.taxa_juros}% a.m.`;
    document.getElementById('contaEmprestimoProximaParcela').textContent = formatarDinheiro(emprestimo.proximaParcela.valor_parcela);
    document.getElementById('contaEmprestimoParcelaTexto').textContent  = `${emprestimo.parcelasPagas} de ${emprestimo.nr_parcelas}`;
    document.getElementById('contaEmprestimoBarraId').style.width       = `${porcentagem}%`;
    document.getElementById('contaEmprestimoVencimento').textContent    = formatarData(emprestimo.proximaParcela.dt_vencimento);

    const badge = document.getElementById('contaEmprestimoStatusBadge');
    badge.textContent = capitalize(emprestimo.ds_status);
    badge.className   = `contaBadge ${emprestimo.ds_status === 'aprovado' ? 'concluida' : 'pendente'}`;
}
//
// ================================
// UTILITÁRIOS
// ================================
//
function formatarDinheiro(valor) {
    return Number(valor).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

function formatarData(data) {
    return new Date(data).toLocaleDateString('pt-BR');
}

function capitalize(texto) {
    if (!texto) return '';
    return texto.charAt(0).toUpperCase() + texto.slice(1);
}
