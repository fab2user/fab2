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
/*
 add_filter( 'json_serve_request', function( ) {
 
 header( "Access-Control-Allow-Origin: *" );
 
 });
 */

function create_plugin_settings_page()
{
    // Add the menu item and page
    $page_title = 'FABWP Settings Page';
    $menu_title = 'FABWP';
    $capability = 'manage_options';
    $slug = 'fabwp_fields';
    $callback = 'plugin_settings_page_content';
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
        array(
            'uid' => 'awp_search_page_url',
            'label' => 'Search FAB2 page definitive URL',
            'section' => 'our_first_section',
            'type' => 'text',
            'placeholder' => 'http://eubailiff.eu/eu-directory',
            // 'helper' => 'Does this help?',
            // 'supplimental' => 'I am underneath!',
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

// the fab_search_frontend component is a line search intened to be used in the home page.
add_shortcode('cehj-fab-search-frontend', 'fab_search_frontend');
// Register name so it can be used into another 
add_shortcode('cehj-fab-search', 'fab_search');

// This code is a simple view of the search screen to be put on the header of a page.
function fab_search_frontend()
{
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

<div id="annuaire">
<div class="container">
<?php
	echo '<form class="formAnnuaireSC formBailiff" action="' . get_option('awp_search_page_url') . '" method="post" id="formAnnuaireSC" name="annuaire">';
?>
        <h3>Find a bailiff</h3>
        <div class="row">
            <div class="formAnnuaireSC_chps col-sm-12 col-md-4">
                <select id="paysSC" name="paysSC">
<?php
foreach ($countries_array as $key => $value) {
        echo '<option value="' . $key . '">' . $value . '</option>';
    }
?>
                </select>
            </div>
            <div class="formAnnuaireSC_chps col-sm-12 col-md-3">
                 <input type="text" class="inputAnnuaire" id="cpSC" name="cpSC" value="" placeholder="Postcode or town">
            </div>
            <div class="formAnnuaireSC_chps col-sm-12 col-md-2">
                <input type="submit" class="inputAnnuaireValider" name="valider" value="Submit" />
            </div>
        </div>
    </form>
</div>
</div>
    <?php
}
// This FAB search has to be placed into the definitive page.
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

    $countrySC=$_POST['paysSC'];
    $cpSC=$_POST['cpSC'];
    ?>

    <div class="annuaire-form">
        <form class="formAnnuaire formBailiff" action="/wordpress/fab2-search" method="post" id="formAnnuaire"
                  name="annuaire">
            <div class="formAnnuaire_chps">
                    <label class="champAnnuaire pays">Country</label>
                    <select name="paysSC" id="paysSC" class="selectAnnuaire">
    <?php
     foreach ($countries_array as $key => $value) {
        if (isset($countrySC) && $countrySC === $key) {
           echo '<option value="' . $key . '" selected="true">' . $value . '</option>';
		} else {
           echo '<option value="' . $key . '">' . $value . '</option>';
		}
     }
    ?>
                    </select>
            </div>
            <div id="formAllCountriesFields" class="formToggle" style="display:inline;">
                    <div class="formAnnuaire_chps" id="formAnnuaireCp">
                        <label class="champAnnuaire cp">Postcode</label>
						<?php
						if (isset($cpSC)) {
							echo '<input type="text" class="inputAnnuaire" name="cpSC" id="cpSC"  placeholder="Postcode, for example : 1000" value="'. $cpSC . '">';
						} else {
							echo '<input type="text" class="inputAnnuaire" name="cpSC" id="cpSC"  placeholder="Postcode, for example : 1000" >';
						}
						?>
                    </div>
					</div>
            </div>
             <div class="inputAnnuaireValiderContainer">
			    <input type="submit" class="inputAnnuaireValider" name="valider" id="search-button" value="Submit"/>
            </div>
        </form>
    </div>
    <div class="col-md-8 col-xs-12">
        <div class="bailiff">
                 <h2 class="ws-l">
                    <div id="nbrResult"></div><br/><span>Find a bailiff in Europe</span>
                </h2>
                <div id="fab-results" class="entries">
                </div>
        </div>
    </div>
	
    <script>
//jQuery("#search-button").click(function(event){
//        search(event);
//    });

//jQuery("#fab-search-form").submit(function(event){
//        search(event);
//    });
	
	function search(event){
		event.preventDefault();
        var serverUrl = jQuery("#server_url").val();
        var params = {country: jQuery("#fab-country").val(), postalCode: jQuery("#fab-zipcode").val()};
        var query = serverUrl + '?' + jQuery.param(params);
        jQuery.getJSON(query).done(function(data){
			jQuery("#nbrResult").empty();
            jQuery("#fab-results").empty();
            if(data.length > 0){
				jQuery("#nbrResult").append(data.length).append(' results');
                var count = 1;
                data.forEach(function(bailiff){
                    displayBailiff(bailiff, count);
                    count ++;
                })
            }else{
				jQuery("#nbrResult").append(' 0 result');
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
	}
	
	
    function displayBailiff(bailiff, count){
        
		var mainDetail = jQuery('<div class="row">');
		mainDetail.append('<div class="col-sm-8"><i class="fa fa-map-marker" aria-hidden="true"></i><div class="title-container">');
        
		 if(bailiff.address1 === null){
            bailiff.address1 = '';
        }
        if(bailiff.address2 === null){
            bailiff.address2 = '';
        }
        var adress = jQuery('<p class="title"><strong>' + bailiff.address1 + ' ' + bailiff.address2 + '</strong>, ' + bailiff.postalCode + ' ' + bailiff.city + '</p>');
		var name = jQuery('<p class="sub-title">Name : ' + bailiff.name + '</p>');
		var spokenLanguages = jQuery('<p class="spoken-language">Spoken languages :</p>');
		mainDetail.append(adress).append(name).append(spokenLanguages);
		mainDetail.append('</div></div>');
		mainDetail.append('<div class="col-sm-4"><a href="#" class="showDetail" rel="' + count + '">View details</a></div>');
		mainDetail.append('</div>');
		
        var req = {format: "json", q: bailiff.address1 + ', ' + bailiff.address2 + ', ' + bailiff.postalCode + ', ' + bailiff.city + ', ' + jQuery("#fab-country").val()};
        jQuery.ajax(
		{url: 'https://nominatim.openstreetmap.org/search?' + jQuery.param(req),
		type:"GET",
		/* crossDomain: true, 
		headers: {'Access-Control-Allow-Origin': '*'},
		*/
                 success: function(data){
            if(data[0]){
                var geoData = data[0];
                var lat = geoData.lat;
                var lon = geoData.lon;
                var mapId = 'map_' + count;
                div.append('<div class="map-container" id="' + mapId + '"></div>');
                var map = L.map(mapId).setView([lat, lon], 14);
                L.tileLayer('http://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}.png', {
                attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, &copy; <a href="https://carto.com/attribution">CARTO</a>',
                maxZoom: 18
                }).addTo(map);
                var marker = L.marker([lat, lon]).addTo(map);
            }
        }});

        var secondaryDetail =  jQuery('<div id="detail-' + count +'" style="display:none;" class="details">');
		secondaryDetail.append('<div class="row">');
        if(bailiff.phone){
            secondaryDetail.append(jQuery('<div class="col-sm-6 col-xl-6 infos"><i class="fa fa-mobile" aria-hidden="true"></i><p><span>Phone</span>' + bailiff.phone + '</p></div>'));
        }
        if(bailiff.fax){
            secondaryDetail.append(jQuery('<div class="col-sm-6 col-xl-6 infos"><i class="fa fa-fax" aria-hidden="true"></i><p><span>Fax</span>' + bailiff.fax + '</p></div>'));
        }
        if(bailiff.email){
            secondaryDetail.append(jQuery('<div class="col-sm-6 col-xl-6 infos"><i class="fa fa-envelope" aria-hidden="true"></i><p><span>E-mail</span></p><ul><li><a href="mailto:' + bailiff.email + '" target="_blank">' + bailiff.email + '</a></li></ul></div>'));
        }
        if(bailiff.webSite){
            secondaryDetail.append(jQuery('<div class="col-sm-6 col-xl-6 infos"><i class="fa fa-globe" aria-hidden="true"></i><p><span>Website</span><a href="www.intermediance.be" target="_blank">' + bailiff.webSite + '" target="_blank">' + bailiff.webSite + '</a></p></div>'));
        }
        if(bailiff.openHours){
            secondaryDetail.append(jQuery('<div class="col-sm-6 col-xl-6 infos"><p><span>open hours</span>' + bailiff.openHours + '</p></div>'));
        }
		secondaryDetail.append('</div></div>');
		
		var div = jQuery('<div class="entry">');
		div.append(mainDetail);
		div.append(secondaryDetail);
		div.append('</div');
        jQuery("#fab-results").append(div);
    }
    </script>

    <?php
	
	$countrySC=$_POST['paysSC'];
    $cpSC=$_POST['cpSC'];
	echo ' <label>' . $countrySC . ' - ' . $cpSC . '</label>';
	
	if (isset($countrySC) && $countrySC != null && isset($cpSC) && $cpSC  != null) {
        $urlHub = get_option('awp_server_url') . '?country=' . $countrySC . '&postalCode=' . $cpSC;

        $response = wp_remote_get($urlHub);
        //echo var_dump($response['body']);
	    if (is_wp_error($response)) {
		    echo '<h3>Error during server request: server may be offline...</h3>';
	    } else {
		   display_bailiffs_response(wp_remote_retrieve_body($response));
	    }
	}
	
}

function display_bailiffs_response($bailiffsResponse)
{
//	echo var_dump($bailiffsResponse);
	
	$counter = 0;
	$data = json_decode( $bailiffsResponse );
	//echo var_dump($data);
    foreach ($data as $bailiff) {
		echo '<div class="entry">
   <div class="row">
    <div class="col-sm-8"><i class="fa fa-map-marker" aria-hidden="true"></i><div class="title-container">
    <p class="title"><strong>' . $bailiff->address2 . '</strong>,'. $bailiff->postalCode .' ' . $bailiff->city .'</p>
    <p class="sub-title">Name : ' . $bailiff->name . '</p>
    <p class="spoken-language">Spoken languages : ' . $bailiff->spokenLanguage . '</p>
    </div></div>
	
    <div class="col-sm-4"><a href="#" class="showDetail" rel="' . $counter . '">View details</a></div>
  </div>

  <div id="detail-' . $counter . '" style="display:none;" class="details">
    <div class="row">
    <div class="col-sm-6 col-xl-6 infos">
    <i class="fa fa-mobile" aria-hidden="true"></i><p><span>Phone</span>' . $bailiff->phone . '</p></div>

    <div class="col-sm-6 col-xl-6 infos">
    <i class="fa fa-fax" aria-hidden="true"></i><p><span>Fax</span>' . $bailiff->fax . '</p></div>

    <div class="col-sm-6 col-xl-6 infos">
    <i class="fa fa-envelope" aria-hidden="true"></i><p><span>E-mail</span></p>
    <ul><li><a href="mailto:' . $bailiff->email . '" target="_blank">' . $bailiff->email . '</a></li></ul></div>

    <div class="col-sm-6 col-xl-6 infos">
    <i class="fa fa-globe" aria-hidden="true"></i><p><span>Website</span>
    <a href="' . $bailiff->webSite . '" target="_blank">' . $bailiff->webSite . '</a></p></div>

    <div class="col-sm-6 col-xl-6"></div>
    </div>

    <div class="row"></div>
    <div class="row"><div class="col-sm-12"></div></div>
  </div>
</div>';
		$counter = $counter + 1;
		// echo var_dump($bailiff);
    }
}

