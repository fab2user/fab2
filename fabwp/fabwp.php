<?php
/*
Plugin Name: fabwp
Description: Bailiff search plugin for FABII
Version:     0.1
Author:      CEHJ
 */

add_action('admin_menu', 'create_plugin_settings_page');
add_action('admin_init', 'setup_sections');
add_action('admin_init', 'setup_fields');
add_action('wp_enqueue_scripts', 'fabwp_enqueue_scripts');

function create_plugin_settings_page()
{
    // Add the menu item and page
    $page_title = 'FABWP Settings Page';
    $menu_title = 'FABWP';
    $capability = 'manage_options';
    $slug = 'fabwp_fields';
    $callback = array($this, 'plugin_settings_page_content');
    $icon = 'dashicons-admin-plugins';
    $position = 100;
    add_menu_page($page_title, $menu_title, $capability, $slug, 'plugin_settings_page_content', $icon, $position);
}

function plugin_settings_page_content()
{
    ?>
    <div class="wrap">
        <h2>FABWP Settings Page</h2><?php
if (isset($_GET['settings-updated']) && $_GET['settings-updated']) {
        admin_notice();
    }?>
        <form method="POST" action="options.php">
            <?php
settings_fields('fabwp_fields');
    do_settings_sections('fabwp_fields');
    submit_button();?>
        </form>
    </div> <?php
}

function admin_notice()
{?>
<div class="notice notice-success is-dismissible">
        <p>Your settings have been updated!</p>
</div><?php
}

function setup_sections()
{
    add_settings_section('our_first_section', 'Main Settings', 'section_callback', 'fabwp_fields');
}

function section_callback($arguments)
{
    switch ($arguments['id']) {
        case 'our_first_section':
            // echo 'This is the first description here!';
            break;
    }
}

function setup_fields()
{
    $fields = array(
        array(
            'uid' => 'awp_server_url',
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
        array(
            'uid' => 'awp_countries',
            'label' => 'Available countries',
            'section' => 'our_first_section',
            'type' => 'textarea',
            'placeholder' => 'FR - France',
            'supplimental' => "Type one country per line.\nEvery line is in the format \"ISOcode - CountryName\".\nFor example for France it would be : FR - France",
        ),
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
        add_settings_field($field['uid'], $field['label'], 'field_callback', 'fabwp_fields', $field['section'], $field);
        register_setting('fabwp_fields', $field['uid']);
    }
}

function field_callback($arguments)
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

function fabwp_enqueue_scripts()
{
    // wp_enqueue_style('bootstrap', plugin_dir_url(__FILE__) . 'css/bootstrap.min.css');
    // wp_enqueue_style('bootstrap-theme', plugin_dir_url(__FILE__) . 'css/bootstrap-theme.min.css');
    wp_enqueue_style('fabwp', plugin_dir_url(__FILE__) . 'css/fabwp.css');
    wp_enqueue_script('jquery');
    wp_enqueue_style('leaflet-css', plugin_dir_url(__FILE__) . 'js/leaflet/leaflet.css');
    wp_enqueue_script('leaflet', plugin_dir_url(__FILE__) . 'js/leaflet/leaflet.js');
}

add_shortcode('cehj-fab-search', 'fab_search');

function fab_search()
{
    echo '<input id="server_url" type="hidden" value="' . get_option('awp_server_url') . '"/>';
    $countries = explode("\n", get_option('awp_countries'));
    $countries_array = array();
    foreach ($countries as $country) {
        $country_split = explode("-", $country);
        $country_code = trim($country_split[0]);
        $country_name = trim($country_split[1]);
        $countries_array[$country_code] = $country_name;
    }
    ksort($countries_array);
    ?>
    <h2>FAB SEARCH</h2>
    <div id="fab-root">
        <form id="fab-search-form">
            <div>
                <div class="form-line">
                    <label for="country">Country</label>
                    <select name="country" id="fab-country">
                    <?php
foreach ($countries_array as $key => $value) {
        echo '<option value="' . $key . '">' . $value . '</option>';
    }
    ?>
                    </select>
                </div>
                <div class="form-line">
                    <label for="zipcode">ZIP Code</label>
                    <input type="text" name="zipcode" id="fab-zipcode">
                </div>
            </div>
            <div class="form-line right">
                <button type="submit">Search !</button>
            </div>
        </form>
    </div>
    <div id="fab-results">
    </div>
    <script>
    jQuery("#fab-search-form").submit(function(event){
        event.preventDefault();
        var serverUrl = jQuery("#server_url").val();
        var params = {country: jQuery("#fab-country").val(), postalCode: jQuery("#fab-zipcode").val()};
        var query = serverUrl + '?' + jQuery.param(params);
        jQuery.getJSON(query).done(function(data){
            jQuery("#fab-results").empty();
            if(data.length > 0){
                data.forEach(function(bailiff){
                    displayBailiff(bailiff);
                })
            }else{
                jQuery("#fab-results").append(jQuery('<h3>No results found...</h3>'));
            }
        })
        .fail(function(err){
            jQuery("#fab-results").empty();
            if(err && err.message){
              jQuery("#fab-results").append(jQuery('<h3>Error during server request: ' + err.error + '</h3>'));
            }else{
                jQuery("#fab-results").append(jQuery('<h3>Error during server request: server may be offline...</h3>'));
            }
        });
    });
    function displayBailiff(bailiff){
        var div = jQuery('<div class="card">');
        var h = jQuery("<h4>" + bailiff.name + "</h4>");
        if(bailiff.address2 === null){
            bailiff.address2 = '';
        }
        var adr1 = jQuery('<div>' + bailiff.address1 + '</div>');
        var adr2 = jQuery('<div>' + bailiff.address2 + '</div>');
        var zip = jQuery('<span class="zip">' + bailiff.postalCode + '</span>');
        var city = jQuery('<span>' + bailiff.city + '</span>');
        var req = {format: "json", q: bailiff.address1 + ', ' + bailiff.address2 + ', ' + bailiff.postalCode + ', ' + bailiff.city + ', ' + jQuery("#fab-country").val()};
        jQuery.get(location.protocol + '//nominatim.openstreetmap.org/search?' + jQuery.param(req) , function(data){
            if(data[0]){
                var geoData = data[0];
                var lat = geoData.lat;
                var lon = geoData.lon;
                var mapId = 'map' + bailiff.id;
                div.append('<div class="map-container" id="' + mapId + '"></div>');
                var map = L.map(mapId).setView([lat, lon], 14);
                L.tileLayer('http://{s}.tile.cloudmade.com/e7b61e61295a44a5b319ca0bd3150890/997/256/{z}/{x}/{y}.png', {
                attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://cloudmade.com">CloudMade</a>',
                maxZoom: 18
                }).addTo(map);
                var marker = L.marker([lat, lon]).addTo(map);
            }
        });

        div.append(h);
        div.append(adr1, adr2, zip,city);
        if(bailiff.email){
            div.append(jQuery('<div>email: ' + bailiff.email + '</div>'));
        }
        if(bailiff.phone){
            div.append(jQuery('<div>phone: ' + bailiff.phone + '</div>'));
        }
        if(bailiff.fax){
            div.append(jQuery('<div>fax: ' + bailiff.fax + '</div>'));
        }
        jQuery("#fab-results").append(div);
    }
    </script>

    <?php
}
