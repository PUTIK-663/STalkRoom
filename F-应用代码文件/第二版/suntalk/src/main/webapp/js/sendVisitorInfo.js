function sendVisitorInfoToServer(){
    let xhr=new XMLHttpRequest();
    xhr.open('GET','../sendVisitorInfo');
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.onload=function(){
        //回显;
    }
    xhr.send();
}
// sendVisitorInfoToServer();
