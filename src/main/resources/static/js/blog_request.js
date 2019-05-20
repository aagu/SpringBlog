function updateFragment(html) {
    var main = $('#main');
    main.hide("fade", 50, function () {
        main.empty();
        main.html(html);
        main.show("fade", {}, 350);
    });
}

function getPage(page) {
    if (page == null) {
        page = 0;
    }
    page -= 1;
    $.get('/blog?start=' + (page*5+1) + '&end=' + (page*5+3), function (d, s, t) {
        var result = t.responseText;
        updateFragment(result);
    });
}