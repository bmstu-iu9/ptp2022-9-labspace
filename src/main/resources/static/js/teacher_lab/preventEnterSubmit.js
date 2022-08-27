$(document).on("keydown", ":input:not(textarea)", function(event) {
    return event.key !== "Enter";
});