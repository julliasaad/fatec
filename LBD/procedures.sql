--1
create or replace procedure Ped_cli (pcodcli cliente.cod_cliente%type)
as
    vqtde number (6);
    vcod cliente.cod_cliente%type;
    vnome cliente.nome_cliente%type;

begin
    select cod_cliente into vcod
    from cliente
    where cod_cliente = pcodcli;

    select nome_cliente into vnome
    from cliente
    where cod_cliente = pcodcli;

    select count (*) into vqtde
    from pedido
    where cod_cliente = pcodcli
    and (to_char (sysdate, 'MM') - to_char(prazo_entrega, 'MM'))<=3;

    if vqtde >= 3 then
          insert into tablog values (sysdate, 'Cliente especial - enviar brinde ' || 'Nome: ' || vnome || 'Cod: ' || pcodcli);
    end if;

exception
    when no_data_found then
        raise_application_error (-20001, 'Cliente nao existe !!!');
end;


--2
create or replace procedure per_comissao (pcodvend number)
as
v_total float;
begin
    select sum (item_pedido.quantidade * produto.valor_unitario) into v_total
    from pedido, item_pedido, produto
    where pedido.num_pedido = item_pedido.num_pedido
    and item_pedido.cod_produto = produto.cod_produto
    and pedido.cod_vendedor = pcodvend
    group by pedido.cod_vendedor;

    if v_total < 100 then
       insert into mensagem values (sysdate,'O vendedor ' || pcodvend || ' recebeu 10% de comissão');
       update vendedor set faixa_comissao = 10 where vendedor.cod_vendedor = pcodvend;
    else
       if v_total BETWEEN 100 AND 1000 then
           insert into mensagem values (sysdate,'O vendedor ' || pcodvend || ' recebeu 20% de comissão');
           update vendedor set faixa_comissao = 20 where vendedor.cod_vendedor = pcodvend;
       else
           insert into mensagem values (sysdate,'O vendedor ' || pcodvend || ' recebeu 0% de comissão');
           update vendedor set faixa_comissao = 0 where vendedor.cod_vendedor = pcodvend;
       end if;
   end if;
   commit;

   exception
       when no_data_found then
            RAISE_APPLICATION_ERROR(-20001, 'O vendedor nao existe!');
end;


--3
Create or replace procedure P_ProdPed ( Pcod number)
as
E_pedido exception;
vproduto produto.cod_produto%type;
vpedido itempedido.num_pedido%type;

begin
  select cod_produto into vproduto from produto
  where cod_produto = pcod;

 if vproduto IN (select ip.cod_produto from item_pedido ip, produto p where p.cod_produto = ip.cod_produto and p.cod_produto = Pcod) then
       raise E_pedido;
  end if;

  commit;

  exception
     when no_data_found then
        insert into tab_erro values( sysdate, 'não existe o produto'||pcod);

     when E_pedido then
        insert into tab_erro values (sysdate,' produto não tem pedido -  '|| pcod);
        delete produto where cod_produto = pcod;
        rollback;
end;


--4
Create or replace procedure Insere_Ped (pcodprod produto.cod_produto%type, pnumped pedido.num_pedido%type, pcodcli cliente.cod_cliente%type, pcodven vendedor.cod_vendedor%type)
as
    vcod produto.cod_produto%type;
    vdata pedido.prazo_entrega%type;

begin
    select cod_produto into vcod
    from produto
    where cod_produto = pcodprod;

    if pcodprod between 1 and 10 then
        vdata := sysdate + 15;
    end if;

    if pcodprod between 11 and 20 then
        vdata := sysdate + 30;
    end if;

    insert into pedido values (pnumped, vdata, pcodcli, pcodven);

exception
    when no_data_found
        raise_application_error (-20001, 'Produto nao existe !!!');
end;


--5
create or replace procedure Insere_itens (pcodprod produto.cod_produto%type, pnumped pedido.num_pedido%type, pqtde item_pedido.quantidade%type)
as
    vcod produto.cod_produto%type;
    vpreco produto.valor_unitario%type;
    vqtde item_pedido.quantidade%type;

begin
    select cod_produto into vcod
    from produto
    where cod_produto = pcodprod;

    select valor_unitario into vpreco
    from produto
    where cod_produto = pcodprod;

    select qtde_estoque into vqtde
    from produto
    where cod_produto = pcodprod;

    if vqtde < 0 then
          raise_application_error (-20001, 'Quantidade incorreta!');
    elsif
          vqtde < pqtde then
                raise_application_error (-20001, 'Quantidade em estoque insuficiente do produto desejado!');
          else
                insert into item_pedido values (pnumped, pcodprod, pqtde, vpreco);
    end if;

exception
    when no_data_found
        raise_application_error (-20001, 'Produto nao existe !!!');
end;
