<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\ProductoController;

// Rutas para productos
Route::get('/productos', [ProductoController::class, 'index']);
Route::get('/productos/create', [ProductoController::class, 'create']);
Route::post('/productos', [ProductoController::class, 'store']);
Route::get('/productos/{id}/edit', [ProductoController::class, 'edit']);
Route::post('/productos/{id}/update', [ProductoController::class, 'update']);
Route::get('/productos/{id}/delete', [ProductoController::class, 'destroy']);

Route::get('/', function () {
    return view('welcome');
});
