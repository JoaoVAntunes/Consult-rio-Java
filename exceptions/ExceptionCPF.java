// Esta clase deriva da classe Exception, 

public class ExceptionCPF extends Exception{

    public ExceptionCPF(String mensagem){
        super(mensagem);
    }

    public ExceptionCPF(){
        super();
    }
}

/*
class ExcecaoSaldoInsuficiente extends Exception {
    public ExcecaoSaldoInsuficiente(String mensagem){
        super(mensagem);
    }

    public ExcecaoSaldoInsuficiente() {
        super();
    }
} 
*/