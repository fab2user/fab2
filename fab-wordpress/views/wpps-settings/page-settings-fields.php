<?php
/*
 * Basic Section
 */
?>

<?php if ('wpps_field-wsurl' == $field['label_for']) : ?>

	<input id="<?php esc_attr_e('wpps_settings[basic][field-wsurl]'); ?>" name="<?php esc_attr_e('wpps_settings[basic][field-wsurl]'); ?>" class="regular-text" value="<?php esc_attr_e(get_option('wpps_field-wsurl')); ?>" />
	<p class="description">URL of the web service to call for search. Must be in the format: http://service_adresse:port</p>

<?php endif; ?>

