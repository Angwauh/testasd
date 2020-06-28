<%--
  Created by IntelliJ IDEA.
  User: ***
  Date: 2020/5/18
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <style>
        body {
            background-color: #c7edcc;
        }
    </style>
    <script type="text/javascript">
        function addfile() {
            var file = document.getElementById("file");
            var div = document.createElement("div");
            var input = document.createElement("input");
            input.type = "file";
            input.name = "filename";

            var button = document.createElement("input");
            button.value = "删除";
            button.type = "button";
            button.onclick = function del() {
                this.parentNode.parentNode.removeChild(this.parentNode);
            }
            div.appendChild(input);
            div.appendChild(button);
            file.appendChild(div);

        }


    </script>
</head>
<body>
<form action="fileUploadServlet" method="post" enctype="multipart/form-data">
    <table style="margin: auto">
        <caption>邮件发送页面</caption>
        <tr>
            <td>收件人：</td>
            <td><input type="text" name="to" size="40"/></td>
        </tr>
        <%--<tr>
            <td>邮件服务器：</td>
            <td><input type="text" name="host" size="40" value="pop.qq.com"/></td>
        </tr>--%>
        <%--<tr>
            <td>发件人：</td>
            <td><input type="text" name="from" size="40" value="524937686@qq.com"/></td>
        </tr>--%>
        <%--<tr>
            <td>发件人授权码：</td>
            <td><input type="text" name="pwd" size="40" value="prwckdfsotdxcaaa"/></td>
        </tr>--%>
        <tr>
            <td>邮件主题：</td>
            <td><input type="text" name="subject" size="40"/></td>
        </tr>
        <tr>
            <td>邮件附件：</td>
            <td><input type="button" value="添加附件" onclick="addfile()"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div id="file"></div>
            </td>
        </tr>
        <tr>
            <td>邮件内容</td>
            <td>
                <textarea rows="10" cols="40" name="content"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="发送"/>
            </td>
        </tr>

    </table>
</form>
</body>
</html>
