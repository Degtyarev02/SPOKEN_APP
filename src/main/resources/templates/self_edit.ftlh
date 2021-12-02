<#import "parts/footer.ftlh" as f>
<#import "parts/common.ftlh" as c>
<#assign userhasicon = user.iconname??>

<@c.mainpage>
    <form class="edit_self_profile" action="/main/edit/${user.id}" method="post" enctype="multipart/form-data">
        <div class="editble">
            <h3>Редактирование профиля</h3>
            <input id="file" type="file" name="file"> <label for="file"><img width="200" height="200"
                                                                             src="${userhasicon?then("https://spokenresourcesbucket.s3.us-east-2.amazonaws.com/profile/${user.iconname}", "/static/img/serverimg/profile.png")}"
                                                                             alt=""></label>
            <input type="text" name="username" placeholder="Username" value="${user.username}">
            <textarea class="status_edit_textarea" type="text" name="status"
                      placeholder="Status">${user.status!""}</textarea>
            <button class="save_btn" type="submit">Save</button>
        </div>
    </form>
    <@f.footer></@f.footer>
</@c.mainpage>