/**
 * Objeto para manipulação da imagem
 */
var imagem = "";

/**
 * Define a imagem no modal
 * @Param imagem - Objeto imagem
 */
var defineImagem = function(image){
    imagem = image;
    $('#image-view').attr('src', image.href);
    $('.modal-title').text(image.name);
}

/**
 * Define a altura para a segunda aba do modal, pois como o modal é responsivo a segunda aba fica menor que a aba que contém a imagem, devido ao espaço que o conteúdo ocupa na tela.
 */
 function defineAlturaTab2(){
     var height = $('#image-view')[0].height;
     $('#informacoes').css("height", height);
}
 
/**
 * Define as informações da imagem para a segunda aba realizando a leitura do objeto imagem
 */
 function defineInformacoesImagem(){
     informacoes = "";
     informacoes += "<p>Imagem: "+ (imagem.name ? imagem.name : "não informado") +"</p><br>";
     informacoes += "<p>Participante: "+ (imagem.participante ? imagem.participante : "não informado") +"</p><br>";
     informacoes += "<p>Formato: "+ (imagem.formato ? imagem.formato : "não informado") +"</p><br>";
     informacoes += "<p>Tamanho: "+ (imagem.tamanho? imagem.tamanho : "não informado") +"</p><br>";
     $('#dados-imagem').empty();
     $('#dados-imagem').append(informacoes);
 }
 
function disativaTabPorId(id){
    $("#"+id).css("pointer-events","none"); 
}

function ativaTabPorId(id){
    $("#"+id).css("cursor","pointer"); 
    $("#"+id).css("pointer-events", "all"); 
}

/**
 * Realiza as ações definidas por cada aba
 */
function acoesTabs(id){
    if(id == "tab1"){
           disativaTabPorId('tab1');
           ativaTabPorId('tab2');    
       }else{
           disativaTabPorId('tab2'); 
           ativaTabPorId('tab1'); 
           defineAlturaTab2();
           defineInformacoesImagem();
       }
}
/**
 * Este bloco executa somente depois que o conteúdo está pronto na tela
 */
$(document).ready(function() {
    function buscarPagina(){
        limparListaDeImagens();
        construirListaDeImagensParaView("js/images.json", 1);
    }
    
    function limparListaDeImagens(){
        $('#lista-imagens').empty();
    }
    
    /**
     * Faz a leitura no json e monta a lista de imagens para visualização na tela
     */
    function construirListaDeImagensParaView(url, pagina){
        var output = ""; 
        var informacoes = "";
        $.getJSON(url, function(data){
               construirPaginacao(data.paginas);
               $.each(data.image, function(key, value) {
                    var json = JSON.stringify(value);
                    output += '<div class="portlet-image"><img src="'+value.href+'" data-toggle="modal" onclick=' + "'" + 'defineImagem(' + json + ")"+ "'" + 'href="#myModal" alt="'+value.name+'" style="cursor:zoom-in; border-radius:0" class="img-thumbnail"></div>';

                });
             $('#lista-imagens').append(output);
        });
    }
    
    function construirPaginacao(paginas){
        var items = "<li class='page-item'><a class='page-link' href='#'>Anterior</a></li>";
        for(var index = 0; index < paginas; index++){
           
                items += "<li class='page-item'><a class='page-link' href='#'>" + (index+1) + "</a></li>";
           
        }
       
            items += "<li class='page-item'><a class='page-link' href='#'>Próximo</a></li>";
      
        $('#paginacao').append(items);
    }
    
    
    //chamando a montagem da lista
    construirListaDeImagensParaView("js/images.json", 1);
    
    /**
     * Animação para modal
     */
    function modalAnimated(x) {
        $('.modal .modal-dialog').attr('class', 'modal-dialog  ' + x + '  animated modal-lg');
    };
    
    /**
     * Habilita as tabs e define a tab da imagem como ativa para a próxima vez que o modal for aberto
     */
    function resetaTabs(){
        $('#imagem').attr('class','tab-pane fade show active');
        $('#informacoes').attr('class','tab-pane fade');
        $('#tab1').attr('class','nav-link active');
        $('#tab2').removeClass("active");
    }
    
    /**
     * Evento de abertura
     */
    $('#myModal').on('show.bs.modal', function (e) {
        modalAnimated("zoomInUp");
    });
    
     /**
     * Evento de fechamento
     */
    $('#myModal').on('hide.bs.modal', function (e) {
        resetaTabs();
        ativaTabPorId("tab1");
        ativaTabPorId("tab2");
        modalAnimated("zoomOutDown");
    });
});

