<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>room.jsp - ${roomName}</title>
        <style>
            body {
                margin: 0 1vw;
            }
            .container {
                width: 78vw;
                margin: 1em 0em;
                display: flex;
            }
            .container.left {
                margin-right: 20vw;
                justify-content: flex-start;
            }
            .container.right {
                margin-left: 20vw;
                justify-content: flex-end;
            }
            .item {
                border: 5px solid;
                border-radius: 10px;
                color: black;
            }
            .item.right {
                background-color: pink;
                border-color: pink;
            }
            .item.left {
                background-color: lightgreen;
                border-color: lightgreen;
            }
        </style>
    </head>
    <body>
        <h2>${roomName}</h2>
        <p>${ message } | <a href="../logout">ログアウト</a></p>
        <input id="message">
        <button onclick="send()">送信</button>
        <hr />
        <button onclick="leave()">終了</button>
        <div id="history"></div>
        <script>
            const ws = new WebSocket("ws://chat.local:8080/Chat/rchat/${roomId}");
            ws.onmessage = function(e){
	            let obj = JSON.parse(e.data);
	            let side = obj.from == "me" ? "right" : "left";
	            let body = obj.from == "me" ? obj.body : obj.from + ":" + obj.body;
	            let h = document.querySelector("#history");
	            h.innerHTML += "<div class='container " + side + "'>"
	                + "<div class='item " + side + "'>" + body  + "</div></div>";
            }

            function send(){
	            let m = document.querySelector("#message");
	            ws.send(m.value);
            }
            
            function leave(){
            	ws.close();
            }
        </script>
    </body>
</html>