PGDMP     )                    x            market    12.1    12.1 %    &           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            '           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            (           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            )           1262    41109    market    DATABASE     �   CREATE DATABASE market WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';
    DROP DATABASE market;
                postgres    false            �            1259    41141    buyers    TABLE     �   CREATE TABLE public.buyers (
    id bigint NOT NULL,
    firstname character varying(50),
    lastname character varying(50)
);
    DROP TABLE public.buyers;
       public         heap    postgres    false            �            1259    41139    buyers_id_seq    SEQUENCE     v   CREATE SEQUENCE public.buyers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.buyers_id_seq;
       public          postgres    false    203            *           0    0    buyers_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.buyers_id_seq OWNED BY public.buyers.id;
          public          postgres    false    202            �            1259    41149    products    TABLE        CREATE TABLE public.products (
    id bigint NOT NULL,
    productname character varying(50),
    expenses double precision
);
    DROP TABLE public.products;
       public         heap    postgres    false            �            1259    41147    products_id_seq    SEQUENCE     x   CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.products_id_seq;
       public          postgres    false    205            +           0    0    products_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;
          public          postgres    false    204            �            1259    41161 	   purchases    TABLE     �   CREATE TABLE public.purchases (
    id bigint NOT NULL,
    buyers_id bigint NOT NULL,
    products_id bigint NOT NULL,
    purchases_date date
);
    DROP TABLE public.purchases;
       public         heap    postgres    false            �            1259    41157    purchases_buyers_id_seq    SEQUENCE     �   CREATE SEQUENCE public.purchases_buyers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.purchases_buyers_id_seq;
       public          postgres    false    209            ,           0    0    purchases_buyers_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.purchases_buyers_id_seq OWNED BY public.purchases.buyers_id;
          public          postgres    false    207            �            1259    41155    purchases_id_seq    SEQUENCE     y   CREATE SEQUENCE public.purchases_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.purchases_id_seq;
       public          postgres    false    209            -           0    0    purchases_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;
          public          postgres    false    206            �            1259    41159    purchases_products_id_seq    SEQUENCE     �   CREATE SEQUENCE public.purchases_products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.purchases_products_id_seq;
       public          postgres    false    209            .           0    0    purchases_products_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.purchases_products_id_seq OWNED BY public.purchases.products_id;
          public          postgres    false    208            �
           2604    41144 	   buyers id    DEFAULT     f   ALTER TABLE ONLY public.buyers ALTER COLUMN id SET DEFAULT nextval('public.buyers_id_seq'::regclass);
 8   ALTER TABLE public.buyers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    203    202    203            �
           2604    41152    products id    DEFAULT     j   ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);
 :   ALTER TABLE public.products ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    204    205            �
           2604    41164    purchases id    DEFAULT     l   ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);
 ;   ALTER TABLE public.purchases ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    206    209    209            �
           2604    41165    purchases buyers_id    DEFAULT     z   ALTER TABLE ONLY public.purchases ALTER COLUMN buyers_id SET DEFAULT nextval('public.purchases_buyers_id_seq'::regclass);
 B   ALTER TABLE public.purchases ALTER COLUMN buyers_id DROP DEFAULT;
       public          postgres    false    207    209    209            �
           2604    41166    purchases products_id    DEFAULT     ~   ALTER TABLE ONLY public.purchases ALTER COLUMN products_id SET DEFAULT nextval('public.purchases_products_id_seq'::regclass);
 D   ALTER TABLE public.purchases ALTER COLUMN products_id DROP DEFAULT;
       public          postgres    false    209    208    209                      0    41141    buyers 
   TABLE DATA           9   COPY public.buyers (id, firstname, lastname) FROM stdin;
    public          postgres    false    203   V'                 0    41149    products 
   TABLE DATA           =   COPY public.products (id, productname, expenses) FROM stdin;
    public          postgres    false    205   (       #          0    41161 	   purchases 
   TABLE DATA           O   COPY public.purchases (id, buyers_id, products_id, purchases_date) FROM stdin;
    public          postgres    false    209   �(       /           0    0    buyers_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.buyers_id_seq', 11, true);
          public          postgres    false    202            0           0    0    products_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.products_id_seq', 7, true);
          public          postgres    false    204            1           0    0    purchases_buyers_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.purchases_buyers_id_seq', 8, true);
          public          postgres    false    207            2           0    0    purchases_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.purchases_id_seq', 42, true);
          public          postgres    false    206            3           0    0    purchases_products_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.purchases_products_id_seq', 8, true);
          public          postgres    false    208            �
           2606    41146    buyers buyers_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.buyers
    ADD CONSTRAINT buyers_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.buyers DROP CONSTRAINT buyers_pkey;
       public            postgres    false    203            �
           2606    41154    products products_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public            postgres    false    205            �
           2606    41168    purchases purchases_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_pkey;
       public            postgres    false    209            �
           1259    41174    fki_buyers_id    INDEX     H   CREATE INDEX fki_buyers_id ON public.purchases USING btree (buyers_id);
 !   DROP INDEX public.fki_buyers_id;
       public            postgres    false    209            �
           1259    41185    fki_products_id_to_purchases    INDEX     Y   CREATE INDEX fki_products_id_to_purchases ON public.purchases USING btree (products_id);
 0   DROP INDEX public.fki_products_id_to_purchases;
       public            postgres    false    209            �
           2606    41175     purchases buyers_id_to_purchases    FK CONSTRAINT     �   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT buyers_id_to_purchases FOREIGN KEY (buyers_id) REFERENCES public.buyers(id) NOT VALID;
 J   ALTER TABLE ONLY public.purchases DROP CONSTRAINT buyers_id_to_purchases;
       public          postgres    false    2709    203    209            �
           2606    41180 "   purchases products_id_to_purchases    FK CONSTRAINT     �   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT products_id_to_purchases FOREIGN KEY (products_id) REFERENCES public.products(id) NOT VALID;
 L   ALTER TABLE ONLY public.purchases DROP CONSTRAINT products_id_to_purchases;
       public          postgres    false    205    2711    209               �   x�U�;1Dk�0���]8,-�+@HT��B�~Ȟar#��6�b�{�Lp�Er���\��-
3�"i�\QP�#&���O�f$8Q�։(�Vpk>-t�q�Q	��u�9��Ӣ��J7N�F|�)�STvl�L���*�J/�n�,b�'���2Q�׵���#�F�%�����c��¾         {   x�̱�@D�x�
*@{������@"G$��8	�@�1&�z�	�qb��nx�5v,���Q\�g~����1���_25z�)���ӂ٧TJnY�8D��O�EG�n��{Vtn���� T�F      #   �   x�e�I�0е�]R�x�K���J�����Lf����zU�X!5ĒȊ�	/N�:�#�h��i�G�'�@Z���1Q�Gޞ��&ך!��b-c�x�0ɚS��Yi%�=�\-{/�1��b�)Z��C�d(�d�k�5����Y����tdq��0-��_�۳1�CB�I����^al     