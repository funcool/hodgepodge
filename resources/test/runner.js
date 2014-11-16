page = new require('webpage').create();


page.onConsoleMessage = function(msg, lineNo, sourceId) {
    console.log("λ", msg);
};

page.onError = function(msg, trace) {
    console.error(msg);
    phantom.exit(1);
};

page.open("resources/test/index.html", function(status){
    if (status === 'fail') {
        console.error("Something went wrong");
        phantom.exit(1);
    } else {
        phantom.exit(0);
    }
});
