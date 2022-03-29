let date = Date.now();
let times = 0;
$("#date").attr({
    'value':date
});
let sel_img = $("#val_code_img");
sel_img.attr({
    'src': '/microservices/GetVCode?date=' + date + '&times=0'
});
sel_img.click(function (){
    times++;
    $(this).attr({
        'src': '/microservices/GetVCode?date=' + date + '&times='+times
    });
});
//输入检测
let ipt_info = document.getElementsByClassName("ipt_info");
let ipt_info_content = ["email","password","c_password","val_code","date"];
let ipt_info_tip = document.getElementsByClassName('ipt_info_tip');
let email_loc = 0,password_loc = 1,c_password_loc = 2,val_code_loc= 3,date_loc = 4;
let bind_ok = false;
function check_input(){
    let next_ok = true;
    //绑定输入框监听
    if (!bind_ok){
        for (let i = 0;i<ipt_info.length;i++){
            ipt_info[i].addEventListener('change',function (){
                ipt_info[i].style.outlineColor = "black";
                ipt_info_tip[i].style.display = "none";
            });
        }
        bind_ok = true;
    }
    //判断是否输入完整
    for (let i = 0;i<ipt_info.length;i++){
        ipt_info_content[i] = ipt_info[i].value;
        if (ipt_info_content[i]==="") {
            ipt_info[i].focus();
            ipt_info[i].style.outlineColor = "red";
            ipt_info_tip[i].style.display = "block";
            next_ok = false;
            break;
        }
    }
    if (!next_ok) return ;
    else if(ipt_info_content[email_loc].search("@")===-1){
        alert("无效的邮件地址!");
        ipt_info[email_loc].focus();
        ipt_info[email_loc].style.outlineColor = "red";
    }
    else if(ipt_info_content[1]!==ipt_info_content[2]) {
        ipt_info[c_password_loc].focus();
        ipt_info[c_password_loc].style.outlineColor = "red";
        ipt_info_tip[c_password_loc].style.display = "block";
    }
    else if(!$('#agreement').prop('checked')) alert("请先同意《办法》!");
    else submitInfo();
}
function submitInfo(){
    ipt_info_content[date_loc] = $('#date').val();
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if(xhr.responseText!==""){
                let recvText = JSON.parse(xhr.responseText);
                if(recvText["respCode"]==="1201"){
                    ipt_info[val_code_loc].focus();
                    ipt_info[val_code_loc].style.outlineColor = "red";
                    document.getElementById("val_code_img").click();
                }
            }
        }
    };
    xhr.open("POST", "/account/register");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("email="+ipt_info_content[email_loc]
        +"&password="+ipt_info_content[c_password_loc]
        +"&date="+ipt_info_content[date_loc]
        +"&val_code="+ipt_info_content[val_code_loc]);
}
