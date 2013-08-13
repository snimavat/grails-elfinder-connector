<html>
	<head>
		<meta name="layout" content="main"/>
		<link rel="stylesheet" type="text/css" media="screen" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/smoothness/jquery-ui.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>				
		<script type="text/javascript" src="http://elfinder.org/demo/js/elfinder.min.js"></script>
  	 <link rel="stylesheet" type="text/css" href="http://elfinder.org/demo/css/elfinder.min.css" />
    <link rel="stylesheet" type="text/css" href="http://elfinder.org/demo/css/theme.css" />
		
		
		<script disposition="head"> 
			$().ready(function() {
				var elf = $('#elfinder').elfinder({
					url : 'elfinderConnector/index'  // connector URL (REQUIRED)					
				}).elfinder('instance');
			});			
		</script>
		 	
	</head>
	
	<body>
		<div id="elfinder"></div>
	</body>
</html>