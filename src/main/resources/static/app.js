
/*
 * Copyright (c) 2014, 2018, Marcus Hirt, Miroslav Wengner
 *
 * Robo4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robo4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robo4J. If not, see <http://www.gnu.org/licenses/>.
 */

var myMap = null;
var positionMarkers = null;
var markerImage = '/robo4j_chart_pin.png';
var simulationMode = 'DRIVING';
var simulationCentered = false;

function initMap() {
    var initLocation = {lat: 48.1413458, lng: 11.5591404};
    var directionsDisplay = new google.maps.DirectionsRenderer;
    var directionsService = new google.maps.DirectionsService;
    myMap = new google.maps.Map(document.getElementById('map'), {
        zoom: 5,
        center: initLocation
    });
    directionsDisplay.setMap(myMap);

    calculateAndDisplayRoute(directionsService, directionsDisplay);
}

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    directionsService.route({
        origin: {lat: 48.1413458, lng: 11.5591404},
        destination: {lat: 47.08314, lng: 8.4332734},
        travelMode: google.maps.TravelMode[simulationMode]
    }, function(response, status) {
        if (status == 'OK') {
            directionsDisplay.setDirections(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}


var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#locationState").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/travelerState', function(currentState){
            var currentStateMessage = JSON.parse(currentState.body);
            showCurrentState(currentStateMessage, myMap);
        })
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showCurrentState(message, map){

    console.log("message data: " + JSON.stringify(message, undefined, 2));

    var travelerLocation = message;
    var travelerLatLng = new google.maps.LatLng(travelerLocation.latitude, travelerLocation.longitude );
    if(!simulationCentered){
        map.panTo(travelerLatLng);
        simulationCentered = true;
    }
    if(positionMarkers == null){
        positionMarkers =  new google.maps.Marker({
            position: travelerLatLng,
            map: map,
            icon: markerImage
        });
    } else {
        positionMarkers.setPosition(travelerLatLng);
    }






    $("#currentState").html(JSON.stringify(message, undefined, 2));
}



$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});
