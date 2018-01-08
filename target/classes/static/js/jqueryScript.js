$(document).ready(function() {
	
	// DO GET
	$.ajax({
		type : "GET",
		url : "shipment/all",
		success: function(result){
			$.each(result, function(i, shipment){
				
				var customerRow = '<tr>' +
									'<td>' + shipment.key + '</td>' +
									'<td>' + shipment.value.createdOn + '</td>' +
									'<td>' + shipment.value.updatedOn + '</td>' +
									'<td>' + shipment.value.shipmentType + '</td>' +
								  '</tr>';
				
				$('#customerTable tbody').append(customerRow);
				
	        });
			
			$( "#customerTable tbody tr:odd" ).addClass("info");
			$( "#customerTable tbody tr:even" ).addClass("success");
		},
		error : function(e) {
			alert("ERROR: ", e);
			console.log("ERROR: ", e);
		}
	});
	
	// do Filter on View
	$("#inputFilter").on("keyup", function() {
	    var inputValue = $(this).val().toLowerCase();
	    $("#customerTable tr").filter(function() {
	    	$(this).toggle($(this).text().toLowerCase().indexOf(inputValue) > -1)
	    });
	});
})