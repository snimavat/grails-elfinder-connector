

modules = {
	'elfinder' {		
		resource id:'css', url:[plugin:'elfinder-connector', dir:'css', file:'elfinder.min.css'], disposition:'head'
		resource id:'theme', url:[plugin:'elfinder-connector', dir:'css', file:'theme.css'], disposition:'head'
		resource id:'js', url:[plugin:'elfinder-connector', dir:'js', file:'elfinder.min.js'], disposition:'head'
	}
}