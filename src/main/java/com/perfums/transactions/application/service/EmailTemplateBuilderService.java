package com.perfums.transactions.application.service;

import java.util.List;

public class EmailTemplateBuilderService {

    public static String buildSentOrderHtml(
            String orderId,
            String customerName,
            List<ProductItem> products,
            String total,
            CustomerInfo customerInfo
    ) {
        StringBuilder productRows = new StringBuilder();
        for (ProductItem product : products) {
            productRows.append("<tr>")
                    .append("<td><img alt=\"Producto\" class=\"product-image\" src=\"")
                    .append(product.getImageUrl())
                    .append("\"></td>")
                    .append("<td>").append(product.getDescription()).append("</td>")
                    .append("<td>").append(product.getQuantity()).append("</td>")
                    .append("</tr>");
        }

        return """
                <meta charset="UTF-8">
                <title>Resumen del pedido</title>
                <style>
                    body{font-family:Arial,sans-serif;margin:0;padding:30px;background-color:#f7f7f7;color:#333}
                    .container{background-color:#fff;padding:25px;border-radius:10px;max-width:600px;margin:auto}
                    h1{color:#111;text-align:center}
                    .order-id{text-align:center;font-size:20px;margin-bottom:20px}
                    .greeting{font-size:16px;margin-bottom:20px}
                    .section-title{font-weight:700;margin-top:30px;font-size:18px}
                    table.order-table{width:100%%;border-collapse:collapse;margin-top:10px}
                    table.order-table td,table.order-table th{border:1px solid #ddd;padding:10px;text-align:left;vertical-align:middle}
                    table.order-table th{background-color:#f2f2f2}
                    img.product-image{width:50px;height:auto;border-radius:4px}
                    .total{text-align:right;font-size:18px;margin-top:20px}
                    .customer-info{margin-top:30px}
                    .customer-info p{margin:5px 0}
                </style>
                <div class="container">
                    <h1>Fragances Store</h1>
                    <div class="order-id">Pedido #%s</div>
                    <p class="greeting">
                      <strong>¡Tu pedido ha sido enviado!</strong><br>
                      Hola %s, tu pedido ya fue despachado y está en camino. Pronto llegará a tu dirección.
                    </p>
                    <div class="section-title">Resumen del pedido</div>
                    <table class="order-table">
                        <thead>
                            <tr>
                                <th>Imagen</th>
                                <th>Descripción</th>
                                <th>Cantidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            %s
                        </tbody>
                    </table>
                    <div class="total"><strong>Total:</strong> %s</div>
                    <div class="customer-info">
                        <div class="section-title">Información del cliente</div>
                        <p><strong>%s %s</strong></p>
                        <p>%s</p>
                        <p>%s</p>
                        <p>%s, %s</p>
                        <p>%s</p>
                    </div>
                </div>
                """.formatted(
                orderId,
                customerName,
                productRows.toString(),
                total,
                customerInfo.getFirstName(),
                customerInfo.getLastName(),
                customerInfo.getAddress(),
                customerInfo.getAdditionalAddressInfo(),
                customerInfo.getDepartment(),
                customerInfo.getCountry(),
                customerInfo.getPostalCode()
        );
    }

    public static String buildPaidOrderHtml(
            String orderId,
            String customerName,
            List<ProductItem> products,
            String total,
            CustomerInfo customerInfo
    ) {
        StringBuilder productRows = new StringBuilder();
        for (ProductItem product : products) {
            productRows.append("<tr>")
                    .append("<td><img alt=\"Producto\" class=\"product-image\" src=\"")
                    .append(product.getImageUrl())
                    .append("\"></td>")
                    .append("<td>").append(product.getDescription()).append("</td>")
                    .append("<td>").append(product.getQuantity()).append("</td>")
                    .append("</tr>");
        }

        return """
                <meta charset="UTF-8">
                <title>Resumen del pedido</title>
                <style>
                    body{font-family:Arial,sans-serif;margin:0;padding:30px;background-color:#f7f7f7;color:#333}
                    .container{background-color:#fff;padding:25px;border-radius:10px;max-width:600px;margin:auto}
                    h1{color:#111;text-align:center}
                    .order-id{text-align:center;font-size:20px;margin-bottom:20px}
                    .greeting{font-size:16px;margin-bottom:20px}
                    .section-title{font-weight:700;margin-top:30px;font-size:18px}
                    table.order-table{width:100%%;border-collapse:collapse;margin-top:10px}
                    table.order-table td,table.order-table th{border:1px solid #ddd;padding:10px;text-align:left;vertical-align:middle}
                    table.order-table th{background-color:#f2f2f2}
                    img.product-image{width:50px;height:auto;border-radius:4px}
                    .total{text-align:right;font-size:18px;margin-top:20px}
                    .customer-info{margin-top:30px}
                    .customer-info p{margin:5px 0}
                </style>
                <div class="container">
                    <h1>Fragances Store</h1>
                    <div class="order-id">Pedido #%s</div>
                    <p class="greeting">
                        <strong>¡Gracias por tu compra!</strong><br>
                        Hola %s, estamos preparando tu pedido para enviarlo. Te notificaremos cuando haya sido enviado.
                    </p>
                    <div class="section-title">Resumen del pedido</div>
                    <table class="order-table">
                        <thead>
                            <tr>
                                <th>Imagen</th>
                                <th>Descripción</th>
                                <th>Cantidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            %s
                        </tbody>
                    </table>
                    <div class="total"><strong>Total:</strong> %s</div>
                    <div class="customer-info">
                        <div class="section-title">Información del cliente</div>
                        <p><strong>%s %s</strong></p>
                        <p>%s</p>
                        <p>%s</p>
                        <p>%s, %s</p>
                        <p>%s</p>
                    </div>
                </div>
                """.formatted(
                orderId,
                customerName,
                productRows.toString(),
                total,
                customerInfo.getFirstName(),
                customerInfo.getLastName(),
                customerInfo.getAddress(),
                customerInfo.getAdditionalAddressInfo(),
                customerInfo.getDepartment(),
                customerInfo.getCountry(),
                customerInfo.getPostalCode()
        );
    }


    public static String buildCanceledOrderHtml(
            String orderId,
            String customerName,
            List<ProductItem> products,
            String total
    ) {
        StringBuilder productRows = new StringBuilder();
        for (ProductItem product : products) {
            productRows.append("<tr>")
                    .append("<td><img alt=\"Producto\" class=\"product-image\" src=\"")
                    .append(product.getImageUrl())
                    .append("\"></td>")
                    .append("<td>").append(product.getDescription()).append("</td>")
                    .append("<td>").append(product.getQuantity()).append("</td>")
                    .append("</tr>");
        }

        return """
                <meta charset="UTF-8">
                <title>Pedido cancelado</title>
                <style>
                    body{font-family:Arial,sans-serif;margin:0;padding:30px;background-color:#f7f7f7;color:#333}
                    .container{background-color:#fff;padding:25px;border-radius:10px;max-width:600px;margin:auto}
                    h1{color:#111;text-align:center}
                    .order-id{text-align:center;font-size:20px;margin-bottom:20px}
                    .greeting{font-size:16px;margin-bottom:20px}
                    .section-title{font-weight:700;margin-top:30px;font-size:18px}
                    table.order-table{width:100%%;border-collapse:collapse;margin-top:10px}
                    table.order-table td,table.order-table th{border:1px solid #ddd;padding:10px;text-align:left;vertical-align:middle}
                    table.order-table th{background-color:#f2f2f2}
                    img.product-image{width:50px;height:auto;border-radius:4px}
                    .total{text-align:right;font-size:18px;margin-top:20px}
                </style>
                <div class="container">
                    <h1>Fragances Store</h1>
                    <div class="order-id">Pedido #%s</div>
                    <p class="greeting">
                        <strong>Tu pedido ha sido cancelado.</strong><br>
                        Hola %s, no hemos detectado el pago correspondiente a tu pedido. Por esta razón, el pedido ha sido cancelado automáticamente.
                    </p>
                    <div class="section-title">Resumen del pedido</div>
                    <table class="order-table">
                        <thead>
                            <tr>
                                <th style="width:60px">Imagen</th>
                                <th>Descripción</th>
                                <th>Cantidad</th>
                            </tr>
                        </thead>
                        <tbody>
                            %s
                        </tbody>
                    </table>
                    <div class="total"><strong>Total:</strong> %s</div>
                </div>
                """.formatted(orderId, customerName, productRows.toString(), total);
    }

    public static class ProductItem {
        private final String imageUrl;
        private final String description;
        private final String quantity;

        public ProductItem(String imageUrl, String description, String quantity) {
            this.imageUrl = imageUrl;
            this.description = description;
            this.quantity = quantity;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getDescription() {
            return description;
        }

        public String getQuantity() {
            return quantity;
        }
    }

    public static class CustomerInfo {
        private final String firstName;
        private final String lastName;
        private final String address;
        private final String additionalAddressInfo;
        private final String country;
        private final String department;
        private final String postalCode;

        public CustomerInfo(String firstName, String lastName, String address,
                            String additionalAddressInfo, String country,
                            String department, String postalCode) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.additionalAddressInfo = additionalAddressInfo;
            this.country = country;
            this.department = department;
            this.postalCode = postalCode;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAddress() {
            return address;
        }

        public String getAdditionalAddressInfo() {
            return additionalAddressInfo;
        }

        public String getCountry() {
            return country;
        }

        public String getDepartment() {
            return department;
        }

        public String getPostalCode() {
            return postalCode;
        }
    }


}
