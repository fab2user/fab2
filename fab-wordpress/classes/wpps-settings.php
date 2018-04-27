<?php

if (!class_exists('WPPS_Settings')) {

    /**
     * Handles plugin settings and user profile meta fields
     */
    class WPPS_Settings extends WPPS_Module
    {
        // protected $settings;
        // protected static $default_settings;
        protected static $readable_properties = array('settings');
        protected static $writeable_properties = array('settings');

        const REQUIRED_CAPABILITY = 'administrator';

        /*
         * General methods
         */

        /**
         * Constructor
         *
         * @mvc Controller
         */
        protected function __construct()
        {
            // Hook into the admin menu
            add_action('admin_menu', array($this, 'create_plugin_settings_page'));
            // Add Settings and Fields
            add_action('admin_init', array($this, 'setup_sections'));
            add_action('admin_init', array($this, 'setup_fields'));
        }

        public function create_plugin_settings_page()
        {
            // Add the menu item and page
            $page_title = 'FABWP Settings Page';
            $menu_title = 'FABWP';
            $capability = 'manage_options';
            $slug = 'fabwp_fields';
            $callback = array($this, 'plugin_settings_page_content');
            $icon = 'dashicons-admin-plugins';
            $position = 100;
            add_menu_page($page_title, $menu_title, $capability, $slug, $callback, $icon, $position);
        }

        public function plugin_settings_page_content()
        {
            ?>
			<div class="wrap">
				<h2>FABWP Settings Page</h2><?php
if (isset($_GET['settings-updated']) && $_GET['settings-updated']) {
                $this->admin_notice();
            }?>
				<form method="POST" action="options.php">
					<?php
settings_fields('fabwp_fields');
            do_settings_sections('fabwp_fields');
            submit_button();?>
				</form>
			</div> <?php
}

        public function admin_notice()
        {?>
		<div class="notice notice-success is-dismissible">
				<p>Your settings have been updated!</p>
		</div><?php
}

        public function setup_sections()
        {
            add_settings_section('our_first_section', 'Main Settings', array($this, 'section_callback'), 'fabwp_fields');
        }

        public function section_callback($arguments)
        {
            switch ($arguments['id']) {
                case 'our_first_section':
                    // echo 'This is the first description here!';
                    break;
            }
        }

        public function setup_fields()
        {
            $fields = array(
                array(
                    'uid' => 'awp_text_field',
                    'label' => 'Search Web Service URL',
                    'section' => 'our_first_section',
                    'type' => 'text',
                    'placeholder' => 'http://serveur_address:port/method',
                    // 'helper' => 'Does this help?',
                    // 'supplimental' => 'I am underneath!',
                ),
                // ,
                // array(
                //     'uid' => 'awp_password_field',
                //     'label' => 'Sample Password Field',
                //     'section' => 'our_first_section',
                //     'type' => 'password',
                // ),
                // array(
                //     'uid' => 'awp_number_field',
                //     'label' => 'Sample Number Field',
                //     'section' => 'our_first_section',
                //     'type' => 'number',
                // ),
                // array(
                //     'uid' => 'awp_textarea',
                //     'label' => 'Sample Text Area',
                //     'section' => 'our_first_section',
                //     'type' => 'textarea',
                // ),
                // array(
                //     'uid' => 'awp_select',
                //     'label' => 'Sample Select Dropdown',
                //     'section' => 'our_first_section',
                //     'type' => 'select',
                //     'options' => array(
                //         'option1' => 'Option 1',
                //         'option2' => 'Option 2',
                //         'option3' => 'Option 3',
                //         'option4' => 'Option 4',
                //         'option5' => 'Option 5',
                //     ),
                //     'default' => array(),
                // ),
                // array(
                //     'uid' => 'awp_multiselect',
                //     'label' => 'Sample Multi Select',
                //     'section' => 'our_first_section',
                //     'type' => 'multiselect',
                //     'options' => array(
                //         'option1' => 'Option 1',
                //         'option2' => 'Option 2',
                //         'option3' => 'Option 3',
                //         'option4' => 'Option 4',
                //         'option5' => 'Option 5',
                //     ),
                //     'default' => array(),
                // ),
                // array(
                //     'uid' => 'awp_radio',
                //     'label' => 'Sample Radio Buttons',
                //     'section' => 'our_first_section',
                //     'type' => 'radio',
                //     'options' => array(
                //         'option1' => 'Option 1',
                //         'option2' => 'Option 2',
                //         'option3' => 'Option 3',
                //         'option4' => 'Option 4',
                //         'option5' => 'Option 5',
                //     ),
                //     'default' => array(),
                // ),
                // array(
                //     'uid' => 'awp_checkboxes',
                //     'label' => 'Sample Checkboxes',
                //     'section' => 'our_first_section',
                //     'type' => 'checkbox',
                //     'options' => array(
                //         'option1' => 'Option 1',
                //         'option2' => 'Option 2',
                //         'option3' => 'Option 3',
                //         'option4' => 'Option 4',
                //         'option5' => 'Option 5',
                //     ),
                //     'default' => array(),
                // ),
            );
            foreach ($fields as $field) {
                add_settings_field($field['uid'], $field['label'], array($this, 'field_callback'), 'fabwp_fields', $field['section'], $field);
                register_setting('fabwp_fields', $field['uid']);
            }
        }

        public function field_callback($arguments)
        {
            $value = get_option($arguments['uid']);
            if (!$value) {
                $value = $arguments['default'];
            }
            switch ($arguments['type']) {
                case 'text':
                case 'password':
                case 'number':
                    printf('<input name="%1$s" id="%1$s" type="%2$s" placeholder="%3$s" value="%4$s" />', $arguments['uid'], $arguments['type'], $arguments['placeholder'], $value);
                    break;
                case 'textarea':
                    printf('<textarea name="%1$s" id="%1$s" placeholder="%2$s" rows="5" cols="50">%3$s</textarea>', $arguments['uid'], $arguments['placeholder'], $value);
                    break;
                case 'select':
                case 'multiselect':
                    if (!empty($arguments['options']) && is_array($arguments['options'])) {
                        $attributes = '';
                        $options_markup = '';
                        foreach ($arguments['options'] as $key => $label) {
                            $options_markup .= sprintf('<option value="%s" %s>%s</option>', $key, selected($value[array_search($key, $value, true)], $key, false), $label);
                        }
                        if ($arguments['type'] === 'multiselect') {
                            $attributes = ' multiple="multiple" ';
                        }
                        printf('<select name="%1$s[]" id="%1$s" %2$s>%3$s</select>', $arguments['uid'], $attributes, $options_markup);
                    }
                    break;
                case 'radio':
                case 'checkbox':
                    if (!empty($arguments['options']) && is_array($arguments['options'])) {
                        $options_markup = '';
                        $iterator = 0;
                        foreach ($arguments['options'] as $key => $label) {
                            $iterator++;
                            $options_markup .= sprintf('<label for="%1$s_%6$s"><input id="%1$s_%6$s" name="%1$s[]" type="%2$s" value="%3$s" %4$s /> %5$s</label><br/>', $arguments['uid'], $arguments['type'], $key, checked($value[array_search($key, $value, true)], $key, false), $label, $iterator);
                        }
                        printf('<fieldset>%s</fieldset>', $options_markup);
                    }
                    break;
            }
            if ($helper = $arguments['helper']) {
                printf('<span class="helper"> %s</span>', $helper);
            }
            if ($supplimental = $arguments['supplimental']) {
                printf('<p class="description">%s</p>', $supplimental);
            }
        }

        public function activate($network_wide)
        {}
        public function deactivate()
        {}
        public function register_hook_callbacks()
        {}

        public function init()
        {}

        public function upgrade($db_version = 0)
        {}

        protected function is_valid($property = 'all')
        {return true;}
    }

}