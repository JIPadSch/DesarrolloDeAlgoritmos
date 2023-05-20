package estructuraDeDatos.jerarquicas.jerarquicasDinamicas;

public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;

    }

    public boolean insertar(Object nuevo, Object padre, char hijo) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoArbol(nuevo, null, null);
            exito = true;
        } else {
            NodoArbol nodoPadre;
            nodoPadre = insertarAux(padre, this.raiz);
            if (nodoPadre != null) {
                if ((hijo == 'i' || hijo == 'I') && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new NodoArbol(nuevo, null, null));
                    exito = true;
                } else if ((hijo == 'd' || hijo == 'D') && nodoPadre.getDerecho() == null) {
                    nodoPadre.setDerecho(new NodoArbol(nuevo, null, null));
                    exito = true;

                }

            }
        }
        return exito;
    }

    private NodoArbol insertarAux(Object padre, NodoArbol nodo) {
        NodoArbol resp = null;
        if (nodo != null) {
            if (nodo.getElem().equals(padre)) {
                resp = nodo;
            } else {
                resp = insertarAux(padre, nodo.getIzquierdo());
                if (resp == null) {
                    resp = insertarAux(padre, nodo.getDerecho());
                }

            }
        }
        return resp;
    }

    public boolean esVacio() {
        boolean esVacio = this.raiz == null;
        return esVacio;
    }

    public Object padre(Object hijo) {
        Object padre;
        padre = padreAux(hijo, this.raiz);
        return padre;
    }

    private Object padreAux(Object hijo, NodoArbol nodo) {
        Object resp = null;
        if (nodo != null) {

            if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getElem().equals(hijo)) || (nodo.getDerecho() != null && nodo.getDerecho().getElem().equals(hijo))) {
                resp = nodo.getElem();
            } else {
                resp = padreAux(hijo, nodo.getIzquierdo());
                if (resp == null) {
                    resp = padreAux(hijo, nodo.getDerecho());

                }
            }

        }
        return resp;
    }

    public int altura() {
        int altura = -1;
        if (!this.esVacio()) {
            altura = alturaAux(this.raiz);
        }
        return altura;
    }

    private int alturaAux(NodoArbol nodo) {
        /*int altura=-1;
        if (nodo!=null){
        altura=Math.max(recuperarAltura(nodo.getIzquierdo()), recuperarAltura(nodo.getDerecho()))+1;
        }
         */
        int alturaIzq = -1, alturaDer = -1, altura;

        if (nodo.getIzquierdo() != null) {
            alturaIzq = alturaAux(nodo.getIzquierdo());
        }
        if (nodo.getDerecho() != null) {
            alturaDer = alturaAux(nodo.getDerecho());
        }

        if (alturaIzq > alturaDer) {
            altura = alturaIzq;
        } else {
            altura = alturaDer;
        }

        return altura + 1;
    }

    public int nivel(Object elem) {

        return nivelAux(elem, this.raiz);

    }

    private int nivelAux(Object elem, NodoArbol nodo) {
        int nivel = -1;
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                nivel = 0;
            } else {
                nivel = nivelAux(elem, nodo.getIzquierdo());

                if (nivel == -1) {
                    nivel = nivelAux(elem, nodo.getDerecho());
                }
                if (nivel != -1) {
                    nivel++;
                }

            }
        }

        return nivel;
    }

    public void vaciar() {
        this.raiz = null;
    }

    @Override
    public ArbolBin clone() {
        ArbolBin clon = new ArbolBin();
        if (!this.esVacio()) {
            clon.raiz = cloneAux(this.raiz);
        }

        return clon;
    }

    private NodoArbol cloneAux(NodoArbol nodo) {
        NodoArbol nuevo = new NodoArbol(nodo.getElem(), null, null);
        if (nodo.getIzquierdo() != null) {
            nuevo.setIzquierdo(cloneAux(nodo.getIzquierdo()));
        }
        if (nodo.getDerecho() != null) {
            nuevo.setDerecho(cloneAux(nodo.getDerecho()));
        }

        return nuevo;
    }

    public Lista listarPorNiveles() {
        NodoArbol actual;
        Lista lis = new Lista();
        if (this.raiz != null) {
            Cola q = new Cola();
            q.poner(this.raiz);
            while (!q.esVacia()) {
                actual = (NodoArbol) q.obtenerFrente();
                q.sacar();
                lis.insertar(actual.getElem(), lis.longitud() + 1);
                if (actual.getIzquierdo() != null) {
                    q.poner(actual.getIzquierdo());
                }
                if (actual.getDerecho() != null) {
                    q.poner(actual.getDerecho());

                }
            }
        }
        return lis;

    }

    public Lista listarPreorden() {
        Lista lis = new Lista();
        preordenAux(this.raiz, lis);
        return lis;
    }

    private void preordenAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) {
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            preordenAux(nodo.getIzquierdo(), lis);
            preordenAux(nodo.getDerecho(), lis);
        }

    }

    public Lista listarInorden() {
        Lista lis = new Lista();
        inordenAux(this.raiz, lis);
        return lis;
    }

    private void inordenAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) {
            inordenAux(nodo.getIzquierdo(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            inordenAux(nodo.getDerecho(), lis);
        }
    }

    public Lista listarPosorden() {
        Lista posOrden = new Lista();
        posordenAux(this.raiz, posOrden);

        return posOrden;
    }

    private void posordenAux(NodoArbol nodo, Lista lis) {

        if (nodo != null) {
            posordenAux(nodo.getIzquierdo(), lis);
            posordenAux(nodo.getDerecho(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
        }

    }

    public String toString() {
        String cadena = "";
        cadena = tustringAux(this.raiz);
        return cadena;
    }

    private String tustringAux(NodoArbol nodo) {
        String resp = "";
        if (nodo != null) {
            resp = nodo.getElem().toString();
            if (nodo.getIzquierdo() != null) {
                resp += " HI: " + nodo.getIzquierdo().getElem().toString();
            } else {
                resp += " HI: -";
            }

            if (nodo.getDerecho() != null) {
                resp += " HD: " + nodo.getDerecho().getElem().toString() + "\n";

            } else {
                resp += " HD: -\n";
            }
            resp += tustringAux(nodo.getIzquierdo());
            resp += tustringAux(nodo.getDerecho());
        }

        return resp;
    }

    public Lista frontera() {
        Lista hojas = new Lista();
        fronteraAux(this.raiz, hojas);

        return hojas;
    }

    private void fronteraAux(NodoArbol nodo, Lista hojas) {
        if (nodo != null) {
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                hojas.insertar(nodo.getElem(), hojas.longitud() + 1);
            }
            fronteraAux(nodo.getIzquierdo(), hojas);
            fronteraAux(nodo.getDerecho(), hojas);
        }
    }
    
    public Lista obtenerAncestros(Object elem){
    Lista ancestros= new Lista();
    ancestrosAux(this.raiz,ancestros,elem);
    
    return ancestros;
    }

    private boolean ancestrosAux(NodoArbol nodo, Lista ancestros, Object elem) {
    boolean encontrado=false;
    
        if (nodo!=null) {
            if (nodo.getElem().equals(elem)) {
                encontrado=true;
            }
                
            
            if (!encontrado) {
                encontrado=ancestrosAux(nodo.getIzquierdo(),ancestros,elem);
                if (!encontrado) {
                    encontrado=ancestrosAux(nodo.getDerecho(),ancestros,elem);
                }
                if (encontrado) {
                ancestros.insertar(nodo.getElem(),ancestros.longitud()+1 );
            }
                
            }
            
        } 
        return encontrado;
    }

    public Lista obtenerDesendientes(Object elem){
        Lista desendientes=new Lista();
        obtenerDesendientesAux(this.raiz,desendientes,elem);
        return desendientes;
    }

    private boolean obtenerDesendientesAux(NodoArbol nodo, Lista desendientes, Object elem) {
        boolean encontrado=false;
        if(nodo!=null){
            if (nodo.getElem().equals(elem)) {
                encontrado=true;
                obtenerDesendientesAux(nodo.getIzquierdo(),desendientes,elem);
                obtenerDesendientesAux(nodo.getDerecho(),desendientes,elem);
            }

            if (encontrado) {
                
            }
        }
    }

}