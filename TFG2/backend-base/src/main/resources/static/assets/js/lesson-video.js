const btn= document.getElementById("getIframe");

btn.addEventListener('click', function(){
    var parser = new DOMParser();
    var iframeVar = document.getElementById("myIframe").value;
    var htmlDoc = parser.parseFromString(iframeVar, 'text/html');
    document.getElementById("videoOutput").appendChild(htmlDoc.documentElement);
});