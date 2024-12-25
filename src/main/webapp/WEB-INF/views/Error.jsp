<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ERROR</title>
    <link href="./assets/css/bootstrap.min.css" rel="stylesheet">
    <script src="./assets/js/jquery.min.js"></script>
    <script src="./assets/js/bootstrap.min.js"></script>
</head>
<style>
    .all{
        width: 100%;
        height: 100%;
    }
    .first{
        height: 50%;
        width: 100%;
        background-color:#ffffff;
        display: flex;
        justify-content: center;
        align-items: end;
    }
    .second{
        width: 100%;
        background-color: #d9d9d9;
        display: flex;
        flex-direction: column;
        row-gap: 20px;
        justify-content: start;
        padding-bottom: 10%;
    }
    .second div {
        width: 100%;
        display: flex;
        justify-content: center;
    }
    #second3{
        transition: 1s;
        position: relative;
        bottom: -100px;
    }
    .h1first{
        font-size: 1000%;
        color:#b5b4b4;
    }
    .h1second{
        font-size: 500%;
        font-weight: 100;
    }
    a{
        width: 250px;
        height: 40px;
        border-radius: 20px;
        text-align: center;
        padding-top: 10px;
        background-color: #4c4c4c;
        color: white;
    }
    a:hover{
        text-decoration: none;
        background-color: #4c4c4cea;
        color: white;
    }
    a:visited{
        text-decoration: none;
        color: white;
    }
</style>
<body>
    <div class="all">
         <% 
         String eMessage = (String) request.getAttribute("eMessage");
         String maison = (String)  request.getAttribute("maison");
         %>
        <div class="first">
            <h1 class="h1first">Oooops!</h1>
        </div>
        <div class="second">
            <div>
                <h1 class="h1second">Cette manoeuvre est interdite.</h1>
            </div>
            <div>
                <p><%= eMessage != null ? eMessage : "" %></p>
            </div>
            <div id="second3">
                <a href="./<%= maison %>">RENTRER A LA MAISON</a>
            </div>
        </div>
    </div>
</body>
<script>
    window.addEventListener ('load' , function () {
        let el = document.getElementById("second3");
        el.style.bottom = '0px';
    });
</script>
</html>