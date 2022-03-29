//参数检查
function check_input(){
    let ipt_number = document.getElementById("number");
    let ipt_password = document.getElementById("password");
    if(ipt_number.value==="") {
        ipt_number.focus();
        return ;
    }
    if(ipt_password.value==="") {
        ipt_password.focus();
        return ;
    }
    ipt_number.addEventListener('change',function (){
        document.getElementById("wrong_info").style.display = "none";
    });
    ipt_password.addEventListener('change',function (){
        document.getElementById("wrong_info").style.display = "none";
    });
    login(ipt_number.value,ipt_password.value);
}
function login(acNumber,acPassword){
    acNumber = $('#number').val();
    acPassword = $('#password').val();
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if(xhr.responseText!==""){
                let recvText = JSON.parse(xhr.responseText);
                if(recvText["respCode"]!=="2000"){
                    document.getElementById("wrong_info").style.display = "block";
                }else {
                    window.location.href = "/home/chat/";
                }
            }
        }
    };
    xhr.open("POST", "/account/login");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("acNumber="+acNumber+"&"+"acPassword="+acPassword);
}