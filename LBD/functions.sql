
--muda o formato da data para ver hora
create or replace function Ver_hora (pdata pedido.prazo_entrega%type)
return varchar2
as
  vhora varchar2(20);
begin
  vhora := to_char(pdata,&#39;DD-MM- YYYY HH24:MI:SS&#39;);
  return vhora;
end;

--classifica um paciente em Idoso ou Não idoso de acordo com sua idade
CREATE or replace FUNCTION IdadePaciente (pcodPapaciente.codpaciente%type)
RETURN VARCHAR2
AS
  vIdade numeric (6);
BEGIN

  SELECT (sysdate - dtnasc) into vIdade
  FROM Paciente
  WHERE codpaciente = pcodPac;
  IF vIdade &gt; 65 THEN
    RETURN &#39;IDOSO&#39;;
  ELSE
      RETURN &#39;NÃO IDOSO&#39;;
  END IF;

END;

--retorna a quantidade corrente em estoque de um produto
create or replace function Consulta_estoque (pcodprodtb_produto.codprod%type)
return numeric
as
  vqtde numeric(6);

begin

  select qtde_estoque into vqtde
  from tb_produto
  where codprod = pcodprod;

  return vqtde;

end;

--formata um número de telefone
create or replace function NumTel (pnum numeric)
return varchar2
as
  vresultado varchar2(30);
begin

  vresultado := '(' || substr(pnum, 1,2) || ')' || substr (pnum, 3, 4) || '-' || substr(pnum, 7,4);
  return vresultado;

end;

--classifica cliente
create or replace function ContaPedidos (pcodcli tb_cliente.codcli%type)
return varchar2
as
  vqtde numeric(6);
  vnome varchar2(50);
begin

  select nomecli into vnome
  from tb_cliente
  where codcli = pcodcli;

  select count(numpedido) into vqtde
  from tb_pedido
  where codcli = pcodcli;

  if vqtde > 3 then
    return 'Cliente Preferencial - cod: ' || pcodcli || ' nome: ' || vnome;
  elsif (vqtde between 1 and 3) then
    return 'Cliente Normal - cod: ' || pcodcli || ' nome: ' || vnome;
  else
    return 'Cliente Inativo - cod: ' || pcodcli || ' nome: ' || vnome;
  end if;

end;
