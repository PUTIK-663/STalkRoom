//参数检查
function check_input(){
    document.getElementById("wrong_info").style.display = "none";
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

function login(id,password){
    const OK = "1";
    id = $('#number').val();
    password = $('#password').val();
    let timestamp = Date.now().toString();
    let nonce = generateMixed(16);
    let aur = generateAur(id,timestamp,nonce,password);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            if(xhr.responseText!==""){
                let recvText = JSON.parse(xhr.responseText);
                if(recvText["code"]!==OK){
                    document.getElementById("wrong_info").style.display = "block";
                }else {
                    window.location.href = "/home/chat";
                }
            }
        }
    };
    xhr.open("POST", "/user/login");
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send("id="+id+"&timestamp="+timestamp+"&nonce="+nonce+"&aur="+aur);
}
function generateMixed(n) {
    let chars = ['0','1','2','3','4','5','6','7','8','9'
        ,'A','B','C','D','E','F','G','H','I','J','K','L'
        ,'M','N','O','P','Q','R','S','T','U','V','W','X'
        ,'Y','Z'];
    let res = "";
    for(let i = 0; i < n ; i ++) {
        let id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}
function generateAur(id,timestamp,nonce,password){
    let arr = [id,timestamp,nonce,password];
    for (let i=0;i<4;i++){
        for (let j=i;j<4;j++){
            let temp = arr[i];
            if(sort(arr[i],arr[j])!==arr[i]){
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    let aur = "";
    for(let i = 0;i<4;i++){
        aur = aur+arr[i];
    }
    aur = hex_sha1(aur)
    function sort(a,b){
        let len = a.length>b.length?a.length:b.length;
        for (let i = 0;i<len;i++){
            if (a[i]<b[i]){
                return a;
            }else if (a[i]>b[i]){
                return b;
            }
        }
        return a;
    }
    return aur;
}

var hexcase = 0; /* hex output format. 0 - lowercase; 1 - uppercase */
var chrsz = 8; /* bits per input character. 8 - ASCII; 16 - Unicode */
function hex_sha1(s){
    return binb2hex(core_sha1(AlignSHA1(s)));
}
function sha1_vm_test(){
    return hex_sha1("abc") === "a9993e364706816aba3e25717850c26c9cd0d89d";
}
function core_sha1(blockArray){
    var x = blockArray; // append padding
    var w = Array(80);
    var a = 1732584193;
    var b = -271733879;
    var c = -1732584194;
    var d = 271733878;
    var e = -1009589776;
    for (var i = 0; i < x.length; i += 16) // 每次处理512位 16*32
    {
        var olda = a;
        var oldb = b;
        var oldc = c;
        var oldd = d;
        var olde = e;
        for (var j = 0; j < 80; j++) // 对每个512位进行80步操作
        {
            if (j < 16){
                w[j] = x[i + j];
            } else{
                w[j] = rol(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
            }
            var t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)), safe_add(safe_add(e, w[j]), sha1_kt(j)));
            e = d;
            d = c;
            c = rol(b, 30);
            b = a;
            a = t;
        }
        a = safe_add(a, olda);
        b = safe_add(b, oldb);
        c = safe_add(c, oldc);
        d = safe_add(d, oldd);
        e = safe_add(e, olde);
    }
    return [a, b, c, d, e];
}
function sha1_ft(t, b, c, d){
    if (t < 20) return (b & c) | ((~ b) & d);
    if (t < 40) return b ^ c ^ d;
    if (t < 60) return (b & c) | (b & d) | (c & d);
    return b ^ c ^ d; // t<80
}
function sha1_kt(t){
    return (t < 20) ? 1518500249 : (t < 40) ? 1859775393 : (t < 60) ? -1894007588 : -899497514;
}

function safe_add(x, y){

    var lsw = (x & 0xFFFF) + (y & 0xFFFF);

    var msw = (x >> 16) + (y >> 16) + (lsw >> 16);

    return (msw << 16) | (lsw & 0xFFFF);

}
function rol(num, cnt){
    return (num << cnt) | (num >>> (32 - cnt));
}
function AlignSHA1(str){
    var nblk = ((str.length + 8) >> 6) + 1, blks = new Array(nblk * 16);
    for (var i = 0; i < nblk * 16; i++) blks[i] = 0;
    for (i = 0; i < str.length; i++) blks[i >> 2] |= str.charCodeAt(i) << (24 - (i & 3) * 8);
    blks[i >> 2] |= 0x80 << (24 - (i & 3) * 8);
    blks[nblk * 16 - 1] = str.length * 8;
    return blks;
}
function binb2hex(binarray){
    var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    var str = "";
    for (var i = 0; i < binarray.length * 4; i++) {
        str += hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8 + 4)) & 0xF) +
            hex_tab.charAt((binarray[i >> 2] >> ((3 - i % 4) * 8)) & 0xF);
    }
    return str;
}
