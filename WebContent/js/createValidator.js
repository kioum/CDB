/**
 * 
 */

(function ( $ ) {
	var introduced = $('#introduced');
	var discontinued = $('#discontinued');
	
	if(introduced.val() == "")
		discontinued.prop("disabled", true);
	
	introduced.change(function(){
		var introSplit = introduced.val().split('-');
		var discSplit = discontinued.val().split('-');

		var introDate = new Date(introSplit[0], introSplit[1] - 1, introSplit[2]);
		var discDate = new Date(discSplit[0], discSplit[1] - 1, discSplit[2]);
		
		if($("#dateWarning").length > 0) $("#dateWarning").remove();

		if(introDate.getTime() > discDate.getTime()) {
			introduced.css("border","1px solid #FF0000").parent().append("<p id='dateWarning' style='color:#FF0000'>Invalid date. Introduced date must have before discontinued date</p>");
		}else {
			introduced.css("border","1px solid #ccc");
			discontinued.css("border","1px solid #ccc");
			discontinued.prop('disabled', false);
		}
	})
	
	discontinued.change(function(){
		var introSplit = introduced.val().split('-');
		var discSplit = discontinued.val().split('-');

		var introDate = new Date(introSplit[0], introSplit[1] - 1, introSplit[2]);
		var discDate = new Date(discSplit[0], discSplit[1] - 1, discSplit[2]);
		
		if($("#dateWarning").length > 0) $("#dateWarning").remove();

		if(introDate.getTime() > discDate.getTime()) {
			discontinued.css("border","1px solid #FF0000").parent().append("<p id='dateWarning' style='color:#FF0000'>Invalid date. Discontinued date must have after introduced date</p>");
		}else {
			introduced.css("border","1px solid #ccc");
			discontinued.css("border","1px solid #ccc");
		}
	})

}( jQuery ));
