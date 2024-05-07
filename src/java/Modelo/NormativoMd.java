package Modelo;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

public class NormativoMd {

    private String Cod;
    private String Nombre;
    private String Categoria;
    private String Valor;
    private String Cantidad;
    private String Validez;
    private String resp;
    private String msg;

    public String getCod() {
        return Cod;
    }

    public void setCod(String Cod) {
        this.Cod = Cod;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getValidez() {
        return Validez;
    }

    public void setValidez(String Validez) {
        this.Validez = Validez;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
