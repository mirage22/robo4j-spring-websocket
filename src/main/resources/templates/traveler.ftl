<#include "header.ftl">
<div id="map" style="height:484px;"></div>
<!-- Replace the value of the key parameter with your own API key. -->
<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyA4cD6KQsuPnuYqwhITc5HHgNMuHjUe8qc"></script>
<script>
    $(document).ready(function(){
        initMap();
    });
</script>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">Active Traveller:</label>
                    <button id="connect" class="btn btn-default" type="submit">Connect</button>
                    <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
    </div>
<#include "footer.ftl">

