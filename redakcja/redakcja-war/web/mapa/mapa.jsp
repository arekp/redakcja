<%@page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@page language="java" import="java.util.*"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
    <script src="http://maps.google.com/maps?file=api&amp;v=2.73&amp;key=ABQIAAAAnfs7bKE82qgb3Zc2YyS-oBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxSySz_REpPq-4WZA27OwgbtyR3VcA"
         type="text/javascript">
     </script>
<s:property value="%{tekst}" />

MApa :)
<script type="text/javascript">

    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map"));
        map.setCenter(new GLatLng(37.4419, -122.1419), 13);

        // Add 10 markers to the map at random locations
        var bounds = map.getBounds();
        var southWest = bounds.getSouthWest();
        var northEast = bounds.getNorthEast();
        var lngSpan = northEast.lng() - southWest.lng();
        var latSpan = northEast.lat() - southWest.lat();
        for (var i = 0; i < 10; i++) {
          var latlng = new GLatLng(southWest.lat() + latSpan * Math.random(),
                                  southWest.lng() + lngSpan * Math.random());
          map.addOverlay(new GMarker(latlng));
        }
      }
    }

    </script>

<s:property value="%{mapar}" escape="false" />


<div onclick="initialize()" id="map" style="width: 500px; height: 400px">mape ????</div>

<div onclick="initialize1()" id="map1" style="width: 800px; height: 600px">Klienci ????</div>