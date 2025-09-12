<!-- Sidebar -->
<div class="sidebar d-flex flex-column">
    <h3 class="text-white text-center py-3">Shop Admin</h3>
    <a href="#"><i class="fas fa-tachometer-alt me-2"></i> Dashboard</a>

    <!-- Products Menu -->
    <a class="d-flex justify-content-between align-items-center" data-bs-toggle="collapse" href="#productsMenu" role="button" aria-expanded="false" aria-controls="productsMenu">
        <span><i class="fas fa-box-open me-2"></i> Products</span>
        <i class="fas fa-chevron-down"></i>
    </a>
    <div class="collapse ps-4" id="productsMenu">
        <a href="allProducts.jsp" class="d-block py-1">All Products</a>
        <a href="addProduct.jsp" class="d-block py-1">Add Product</a>
    </div>

    <!-- Orders Menu -->
    <a class="d-flex justify-content-between align-items-center" data-bs-toggle="collapse" href="#ordersMenu" role="button" aria-expanded="false" aria-controls="ordersMenu">
        <span><i class="fas fa-shopping-cart me-2"></i> Orders</span>
        <i class="fas fa-chevron-down"></i>
    </a>
    <div class="collapse ps-4" id="ordersMenu">
        <a href="allOrders.jsp" class="d-block py-1">All Orders</a>
        <a href="pendingOrders.jsp" class="d-block py-1">Pending Orders</a>
    </div>

    <!-- Customers Menu -->
    <a class="d-flex justify-content-between align-items-center" data-bs-toggle="collapse" href="#customersMenu" role="button" aria-expanded="false" aria-controls="customersMenu">
        <span><i class="fas fa-users me-2"></i> Customers</span>
        <i class="fas fa-chevron-down"></i>
    </a>
    <div class="collapse ps-4" id="customersMenu">
        <a href="allCustomers.jsp" class="d-block py-1">All Customers</a>
        <a href="vipCustomers.jsp" class="d-block py-1">VIP Customers</a>
    </div>

    <a href="#"><i class="fas fa-cog me-2"></i> Settings</a>
</div>
