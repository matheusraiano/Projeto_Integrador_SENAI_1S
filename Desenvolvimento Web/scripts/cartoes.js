//Código de Matheus Raiano
let numeracao = 1

function cartao() {
    let cartao = document.querySelector("div.cartao")
    let direito = document.querySelector("div.direito")

    if (Number(numeracao) == 1) {
        numeracao ++
        cartao.style.backgroundImage = 'url("../midias/cartao_interlagos.png")'
        direito.innerHTML = `<p>CARTÃO <br>INTERLAGOS</p>`
    } else if (Number(numeracao) == 2) {
        numeracao ++
        cartao.style.backgroundImage = 'url("../midias/cartao_monaco.png")'
        direito.innerHTML = `<p>CARTÃO <br>MÔNACO</p>`
    } else if (Number(numeracao) == 3) {
        numeracao = 1
        cartao.style.backgroundImage = 'url("../midias/cartao_classic.png")'
        direito.innerHTML = `<p>CARTÃO <br>CLASSIC</p>`
    }
}
