<?php

	$screen_name=$_POST['username'];
    
    $count = 20; 
    $retweets = 0; 
 
   
    $oauthAccessToken='3166838406-pbjuGwltKD9DJ8vgAli8vySvIRfOV3sVAJ5VimA';
    $oauthAccessTokenSecret='bQhTX1486UIZz7a7iS0amnORljf1Wz55654c4E26q4uik';
    $oauthConsumerKey='970e9L9glvm9vVlzq0iT3hc11';
    $oauthConsumerSecret='SmSAM0JpiMJ1U7JkQxQHskAcN7vDb82wTy3309C44FzE2yxMWt';
 
    
    $oauth = array(
        'count' => $count,
        'include_rts' => $retweets,
        'oauth_consumer_key' => $oauthConsumerKey,
        'oauth_nonce' => time(),
        'oauth_signature_method' => 'HMAC-SHA1',
        'oauth_timestamp' => time(),
        'oauth_token' => $oauthAccessToken,
        'oauth_version' => '1.0'
    );
 
    $arr = array();
    foreach($oauth as $key => $val)
        $arr[] = $key.'='.rawurlencode($val);
 
   
    $oauth['oauth_signature'] = base64_encode(hash_hmac('sha1', 'GET&'.rawurlencode('https://api.twitter.com/1.1/statuses/home_timeline.json').'&'.rawurlencode(implode('&', $arr)), rawurlencode($oauthConsumerSecret).'&'.rawurlencode($oauthAccessTokenSecret), true));
 
    $arr = array();
    foreach($oauth as $key => $val)
        $arr[] = $key.'="'.rawurlencode($val).'"';
 

    $tweets = curl_init();
    curl_setopt_array($tweets, array(
        CURLOPT_HTTPHEADER => array('Authorization: OAuth '.implode(', ', $arr), 'Expect:'),
        CURLOPT_HEADER => false,
        CURLOPT_URL => 'https://api.twitter.com/1.1/statuses/home_timeline.json?count='.$count.'&include_rts='.$retweets,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_SSL_VERIFYPEER => false,
    ));
    $json = curl_exec($tweets);
    curl_close($tweets);
 
    
  
echo $json;
    
?>