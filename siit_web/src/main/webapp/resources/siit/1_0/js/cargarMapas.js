function crearLayer(wmsUrl, nombreCapa, styles, filter, mbAttr) {
    var resultLayer = new L.TileLayer.WMS(wmsUrl, {
        layers: nombreCapa,
        styles: styles,
        filter: filter,
        format: 'image/png',
        transparent: true,
        version: '1.1.0',
        attribution: mbAttr
    });
    return resultLayer;
}

function crearLayerCQL(wmsUrl, nombreCapa, styles, filter, mbAttr) {
    var resultLayer = new L.TileLayer.WMS(wmsUrl, {
        layers: nombreCapa,
        styles: styles,
        CQL_FILTER: filter,
        format: 'image/png',
        transparent: true,
        version: '1.1.0',
        attribution: mbAttr
    });
    return resultLayer;
}

function cargarCapas(mbAttr, mbUrl, wmsUrl, baseMapa, northEastLat, northEastLng, southWestLat, southWestLng, nombreLayer, pLayers, pStyle, pFilter, centerLat, centerLng, pZoom, pMinZoom, baseLayers, overlays, owsUrl, pSetContent) {

    //-------------Valores por Defecto
    //mbAttr = "Map data &copy; <a href='http://openstreetmap.org'>OpenStreetMap</a> contributors, <a href='http://www.mined.gob.ni/'>MINED</a>";
    //mbUrl =  'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
    //wmsUrl = 'http://inss-mg2-desa2:8380/geoserver/MINED/wms';
    //baseMapa = 'examples.map-i875mjb7';
    //northEastLat = 10.65673828125
    //northEastLng = -89.30810546875
    //southWestLat = 15.05126953125
    //southWestLng = -82.265625
    //centerLat = 12.85
    //centerLng = -85.30
    //pZoom = 7
    //pMinZoom = 7
    //owsUrl = "http://inss-mg2-desa2:8380/geoserver/MINED/ows";
    //pSetContent = "<table style='width:100%'><tr><td rowspan='4' style='width:10%'><img src='http://www.picgifs.com/graphics/k/kawaii/graphics-kawaii-763692.jpg' width='100' height='100'></td></tr><tr><td style='width:10%'><b>C�digo: </b></td><td style='color: blue'>"+data.features[0].properties.codigo+"</td></tr><tr><td><b>Nombre:</b> </td><td style='color: blue'>"+data.features[0].properties.nombre+"</td></tr></table>"


    var nombreLayer = new L.LayerGroup();

    var mbAttr = mbAttr;
    var mbUrl = mbUrl;
    var wmsUrl = wmsUrl;
    var baseMapa = L.tileLayer(mbUrl, {id: baseMapa, attribution: mbAttr});

    var northEast = new L.LatLng(northEastLat, northEastLng);
    var southWest = new L.LatLng(southWestLat, southWestLng);

    var bounds = new L.LatLngBounds(southWest, northEast);


    nombreLayer = new L.TileLayer.WMS(wmsUrl, {
        layers: pLayers,
        styles: pStyle,
        filter: pFilter,
        format: 'image/png',
        transparent: true,
        version: '1.1.0',
        attribution: mbAttr
    });

    var map = L.map('map', {
        center: [centerLat, centerLng],
        zoom: pZoom,
        minZoom: pMinZoom,
        //crs: L.CRS.EPSG4326,
        layers: [baseMapa]
    });

    var options = {
        container_width: "300px",
        group_maxHeight: "80px",
        container_maxHeight: "350px",
        exclusive: true
    };

    var control = L.Control.styledLayerControl(baseLayers, overlays, options);
    map.addControl(control);

    L.control.scale().addTo(map);

    map.on('click', function (e) {
        var params = {
            REQUEST: "GetFeatureInfo",
            BBOX: (map.getBounds().toBBoxString()),
            SERVICE: "WMS",
            INFO_FORMAT: 'text/javascript',
            QUERY_LAYERS: pLayers,
            FEATURE_COUNT: 50,
            Layers: pLayers,
            WIDTH: map.getSize().x,
            HEIGHT: map.getSize().y,
            outputFormat: "text/javascript",
            format_options: "callback:processJSON",
            srs: "EPSG:4326",
            version: "1.3",
            x: parseInt(e.layerPoint.x),
            y: parseInt(e.layerPoint.y)
        };

        var url = owsUrl;
        var popup;

        $.ajax({
            jsonp: false,
            jsonpCallback: 'processJSON',
            data: params,
            type: 'GET',
            url: url,
            async: false,
            dataType: 'jsonp',
            success: function (data) {
                if (data.features.length == 0) {

                }
                else {
                    popup = L.popup()
                        .setLatLng([data.features[0].properties.y, data.features[0].properties.x])
                        .setContent(pSetContent)
                        .openOn(map);
                }
                /**/
            }
        });
    });

    // Insertando una leyenda en el mapa
    /*var legend = L.control({position: 'bottomright'});

     legend.onAdd = function (map) {

     var div = L.DomUtil.create('div', 'info legend');

     div.innerHTML +=
     '<img alt="legend" src=� http://inss-mg2-desa2:8380/geoserver/MINED/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=usa:states� width=�127? height=�120? />';

     return div;

     };

     legend.addTo(map);*/
}