const btn= document.getElementById("getIframe");

btn.addEventListener('click', function(){
    alert("Holaaaaaaaa");
    const iframeVar = document.getElementById("myIframe").value;
    alert("Name: "+ iframeVar);
});