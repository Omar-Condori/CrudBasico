<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;

class ProductoController extends Controller
{
    public function index()
    {
        try {
            $response = Http::timeout(10)->get('http://127.0.0.1:8081/api/productos');
            
            if ($response->successful()) {
                $productos = $response->json();
                Log::info('Productos obtenidos exitosamente', ['count' => count($productos)]);
                return view('productos.index', compact('productos'));
            } else {
                Log::error('Error al obtener productos del backend', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
                return view('productos.index', ['productos' => [], 'error' => 'Error al conectar con el backend']);
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener productos', [
                'message' => $e->getMessage(),
                'trace' => $e->getTraceAsString()
            ]);
            return view('productos.index', ['productos' => [], 'error' => 'Error de conexión: ' . $e->getMessage()]);
        }
    }

    public function create()
    {
        $categorias = Http::get('http://127.0.0.1:8081/api/categorias')->json();
        return view('productos.create', compact('categorias'));
    }

    public function store(Request $request)
    {
        try {
            $data = $request->except('_token');
            if (isset($data['categoria_id'])) {
                $data['categoria'] = ['id' => (int)$data['categoria_id']];
                unset($data['categoria_id']);
            }
            $response = Http::timeout(10)
                ->withHeaders(['Content-Type' => 'application/json'])
                ->post('http://127.0.0.1:8081/api/productos', $data);
            
            if ($response->successful()) {
                Log::info('Producto creado exitosamente');
                return redirect('/productos')->with('success', 'Producto creado exitosamente');
            } else {
                Log::error('Error al crear producto', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
                return back()->with('error', 'Error al crear el producto');
            }
        } catch (\Exception $e) {
            Log::error('Excepción al crear producto', [
                'message' => $e->getMessage()
            ]);
            return back()->with('error', 'Error de conexión: ' . $e->getMessage());
        }
    }

    public function edit($id)
    {
        try {
            $response = Http::timeout(10)->get("http://127.0.0.1:8081/api/productos/$id");
            
            if ($response->successful()) {
                $producto = $response->json();
                return view('productos.edit', compact('producto'));
            } else {
                Log::error('Error al obtener producto para editar', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
                return redirect('/productos')->with('error', 'Error al obtener el producto');
            }
        } catch (\Exception $e) {
            Log::error('Excepción al obtener producto para editar', [
                'message' => $e->getMessage()
            ]);
            return redirect('/productos')->with('error', 'Error de conexión: ' . $e->getMessage());
        }
    }

    public function update(Request $request, $id)
    {
        try {
            $data = $request->except('_token');
            if (isset($data['categoria_id'])) {
                $data['categoria'] = ['id' => (int)$data['categoria_id']];
                unset($data['categoria_id']);
            }
            $response = Http::timeout(10)
                ->withHeaders(['Content-Type' => 'application/json'])
                ->put("http://127.0.0.1:8081/api/productos/$id", $data);
            
            if ($response->successful()) {
                Log::info('Producto actualizado exitosamente');
                return redirect('/productos')->with('success', 'Producto actualizado exitosamente');
            } else {
                Log::error('Error al actualizar producto', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
                return back()->with('error', 'Error al actualizar el producto');
            }
        } catch (\Exception $e) {
            Log::error('Excepción al actualizar producto', [
                'message' => $e->getMessage()
            ]);
            return back()->with('error', 'Error de conexión: ' . $e->getMessage());
        }
    }

    public function destroy($id)
    {
        try {
            $response = Http::timeout(10)->delete("http://127.0.0.1:8081/api/productos/$id");
            
            if ($response->successful()) {
                Log::info('Producto eliminado exitosamente');
                return redirect('/productos')->with('success', 'Producto eliminado exitosamente');
            } else {
                Log::error('Error al eliminar producto', [
                    'status' => $response->status(),
                    'body' => $response->body()
                ]);
                return redirect('/productos')->with('error', 'Error al eliminar el producto');
            }
        } catch (\Exception $e) {
            Log::error('Excepción al eliminar producto', [
                'message' => $e->getMessage()
            ]);
            return redirect('/productos')->with('error', 'Error de conexión: ' . $e->getMessage());
        }
    }
}