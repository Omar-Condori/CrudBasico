<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Productos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="mb-0">Productos</h1>
        <a href="/productos/create" class="btn btn-success">+ Crear Producto</a>
    </div>
    
    @if(session('success'))
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            {{ session('success') }}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    @endif
    
    @if(session('error'))
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            {{ session('error') }}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    @endif
    
    <div class="card shadow-sm">
        <div class="card-body">
            @if(isset($productos) && is_array($productos) && count($productos) > 0)
                <table class="table table-hover align-middle">
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Descripción</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach($productos as $producto)
                            <tr>
                                <td>{{ $producto['nombre'] }}</td>
                                <td>$ {{ number_format($producto['precio'], 2) }}</td>
                                <td>{{ $producto['descripcion'] ?? '-' }}</td>
                                <td>
                                    <a href="/productos/{{ $producto['id'] }}/edit" class="btn btn-sm btn-primary">Editar</a>
                                    <a href="/productos/{{ $producto['id'] }}/delete" class="btn btn-sm btn-danger" onclick="return confirm('¿Seguro que deseas eliminar este producto?')">Eliminar</a>
                                </td>
                            </tr>
                        @endforeach
                    </tbody>
                </table>
            @else
                <div class="alert alert-info mb-0">No hay productos registrados.</div>
            @endif
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>