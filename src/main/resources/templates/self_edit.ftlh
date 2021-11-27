<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit</title>
</head>
<body>

<form action="/main/edit/${user.id}" method="post">
    <input type="text" name="username" placeholder="Username" value="${user.username}">
    <textarea type="text" name="status" placeholder="Status">${user.status}</textarea>
    <button type="submit">Save</button>
</form>

</body>
</html>