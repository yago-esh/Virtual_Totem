# Virtual_Totem
A program to have a synchronized totem for multiple machines.

<h1><span style="font-size:36px;"><strong>VIRTUAL TOTEM</strong></span></h1>

<hr />
<h1><u>INTRODUCCI&Oacute;N</u></h1>

<p>Virtual Totem es un software de sincronizaci&oacute;n de t&oacute;tems virtuales.</p>

<p>Con esta aplicaci&oacute;n se pretende mejorar la funcionalidad ya existente de los t&oacute;tems f&iacute;sicos que estamos utilizando. Para ello la aplicaci&oacute;n cuenta con dos partes.</p>

<p>1.<u>Una aplicaci&oacute;n Servidor</u>: La cual est&aacute; alojada en GVPLocal (10.0.1.95).</p>

<p>2.<u>Una aplicaci&oacute;n Cliente</u>: Que tendr&aacute; cada usuario en su ordenador y la cual consta de una interfaz gr&aacute;fica con la que se interactuar&aacute; para sincronizar el t&oacute;tem con el resto de usuarios.</p>

<p>Cada Cliente se comunica con el servidor, el cual guarda los valores de los t&oacute;tem y notifica al resto de usuarios de su estado.</p>

<p>Esta aplicaci&oacute;n se encuentra en fase de pruebas, y aunque ha sido testeada antes de distribuirse, debido a que se trata de un software con procesos de sincronizaci&oacute;n entre diferentes usuarios, algunas de las funciones no han podido ser probadas completamente, por lo que se recomienda su uso junto a los t&oacute;tem f&iacute;sicos los primeros d&iacute;as mientras se comprueba el correcto funcionamiento de la aplicaci&oacute;n.</p>

<p>En caso de encontrar cualquier error o mal funcionamiento, por favor enviadme un correo a: <a href="mailto:yago.echave@fonetic.com">yago.echave@fonetic.com</a>. Del mismo modo, pod&eacute;is escribirme para quejas y/o sugerencias de mejora de la aplicaci&oacute;n.</p>

<hr />
<h1><u>INSTALACI&Oacute;N</u></h1>

<p>La aplicaci&oacute;n es portable, por lo que no requiere de ninguna instalaci&oacute;n.</p>

<p><a href="https://www.dropbox.com/sh/jqc4p01qfu177bv/AABy0MbhoQnWQfcn8-BQ2c63a?dl=0">DESCARGAR VIRTUAL TOTEM</a></p>

<p>Para que funcione bastar&aacute; con tener java instalado y definir el tipo de archivo &ldquo;.jar&rdquo; para que se ejecute siempre como archivo java.</p>

<p><a href="http://javadl.oracle.com/webapps/download/AutoDL?BundleId=230511_2f38c3b165be4555a1fa6e98c45e0808">DESCARGAR JAVA</a></p>

<hr />
<h1><u>FUNCIONES B&Aacute;SICAS</u></h1>

<ol>
	<li>Coger y soltar el respectivo t&oacute;tem</li>
	<li>A&ntilde;adirse a la cola de espera de un t&oacute;tem</li>
	<li>Forzar la liberaci&oacute;n de un t&oacute;tem</li>
	<li>Notificaci&oacute;n de cierre</li>
	<li>Control de Versiones</li>
</ol>

<hr />
<h1><u>COGER Y SOLTAR EL LOBO</u></h1>

<p>La funcionalidad principal de la aplicaci&oacute;n es la de coger y soltar un t&oacute;tem. Para ello solo deberemos pulsar en uno de los botones que aparecen con el texto &ldquo;<u>Coger </u><u>Totem</u><u>&rdquo;</u></p>

<p><img alt="" data-rich-file-id="94" src="/system/rich/rich_files/rich_files/000/000/094/original/Imagen1.png" /></p>

<p>Autom&aacute;ticamente tu aplicaci&oacute;n pasar&aacute; al estado de &ldquo;retenci&oacute;n de un t&oacute;tem&rdquo;. Y tu bot&oacute;n cambiara de estado a &ldquo;<u>Soltar T&oacute;tem</u>&rdquo;. Si pulsas ahora el bot&oacute;n, el t&oacute;tem se liberar&aacute; y el bot&oacute;n de todos los usuarios volver&aacute; al estado de &ldquo;Coger Totem&rdquo;</p>

<p><img alt="" data-rich-file-id="95" src="/system/rich/rich_files/rich_files/000/000/095/original/Imagen2.png" /></p>

<p>El bot&oacute;n del resto de usuarios cambiar&aacute; de estado. Este se deshabilitar&aacute; y por tanto no podr&aacute; ser usado. Adem&aacute;s el nombre cambiar&aacute; informando al usuario de <u>qui&eacute;n tiene el t&oacute;tem actualmente</u>.</p>

<p><img alt="" data-rich-file-id="96" src="/system/rich/rich_files/rich_files/000/000/096/original/Imagen3.png" /></p>

<hr />
<h1><u>A&Ntilde;ADIRSE A LA COLA</u></h1>

<p>Imaginemos que queremos coger un determinado t&oacute;tem, pero este est&aacute; ocupado. El programa tiene una funcionalidad por la cual nos podemos a&ntilde;adir a una cola de espera para ese t&oacute;tem. Para ello simplemente deberemos pulsar en el bot&oacute;n con el s&iacute;mbolo de informaci&oacute;n que aparece al lado del t&oacute;tem bloqueado. Este bot&oacute;n solo se activar&aacute; cuando el t&oacute;tem est&eacute; retenido por alg&uacute;n usuario.</p>

<p><img alt="" data-rich-file-id="99" src="/system/rich/rich_files/rich_files/000/000/099/original/Imagen8.png" /></p>

<p>Nos aparecer&aacute; la siguiente ventana de informaci&oacute;n, en la cual, si pulsamos el bot&oacute;n &ldquo;OK&rdquo;, autom&aacute;ticamente la ventana se cerrar&aacute; y pasaremos a estar incluidos en la cola, igual que est&aacute; el usuario actual. Y se le notificar&aacute; al usuario del t&oacute;tem de que estamos solicit&aacute;ndolo.</p>

<p><img alt="" data-rich-file-id="101" src="/system/rich/rich_files/rich_files/000/000/101/original/Imagen11.png" /><img alt="" data-rich-file-id="100" src="/system/rich/rich_files/rich_files/000/000/100/original/Imagen12.png" /></p>

<p>En el momento en el que el usuario actual libere el t&oacute;tem, si somos el primer usuario en la cola, nos aparecer&aacute; una ventana emergente la cual nos informar&aacute; de que el t&oacute;tem ha si liberado y de que si queremos cogerlo. Durante este proceso, el bot&oacute;n del resto de usuarios seguir&aacute; desactivado pero cambiar&aacute; al estado &ldquo;En tr&aacute;nsito&rdquo;.</p>

<p>Si aceptamos, autom&aacute;ticamente pasaremos a tener el t&oacute;tem, y el bot&oacute;n del resto de usuarios cambiar&aacute; para mostrarle la informaci&oacute;n de que somos nosotros quienes retenemos el t&oacute;tem.</p>

<p>En caso de que cancelemos o cerremos la ventana, se mandar&aacute; una se&ntilde;al de liberaci&oacute;n del t&oacute;tem, la cual comprobar&aacute; si hay m&aacute;s usuarios en cola. En caso afirmativo, se realizar&aacute; el mismo proceso con el siguiente usuario. Si no hubiera m&aacute;s usuarios en cola, el t&oacute;tem se liberar&iacute;a de forma normal.</p>

<p><img alt="" data-rich-file-id="102" src="/system/rich/rich_files/rich_files/000/000/102/original/Imagen13.png" /></p>

<hr />
<h1><u>FORZAR LA LIBERACI&Oacute;N DEL T&Oacute;TEM</u></h1>

<p>Es posible que en un momento determinado queramos coger el t&oacute;tem pero este est&eacute; bloqueado, y nos demos cuenta de que realmente el usuario que lo est&aacute; bloqueando ya no lo utilice, pero por el motivo que sea, no pueda liberarlo de forma manual (se ha ido a una reuni&oacute;n, se ha dejado el ordenador en la oficina, etc.), o que la aplicaci&oacute;n haya tenido un fallo y se t&eacute; bloqueando un t&oacute;tem cuando no deber&iacute;a.</p>

<p>Por estos motivos, se ha creado una funci&oacute;n de seguridad la cual resetea los valores de un t&oacute;tem, es decir, fuerza su liberaci&oacute;n, vac&iacute;a la cola de usuarios y devuelve al estado &ldquo;Coger T&oacute;tem&rdquo; el bot&oacute;n para todos los usuarios.</p>

<p>Para utilizar esta funci&oacute;n deberemos pinchar en el bot&oacute;n de &ldquo;Alerta&rdquo; que aparece debajo de la aplicaci&oacute;n</p>

<p><img alt="" data-rich-file-id="103" src="/system/rich/rich_files/rich_files/000/000/103/original/Imagen14.png" /></p>

<p>Se nos abrir&aacute; la ventana de desbloqueo, la cual solo tendr&aacute; activos los botones que hacen referencia al t&oacute;tem que est&aacute; actualmente bloqueado. Si pinchamos en el bot&oacute;n, este cambiar&aacute; de color y pasar&aacute; a estar seleccionado.</p>

<p><img alt="" data-rich-file-id="105" src="/system/rich/rich_files/rich_files/000/000/105/original/Imagen15.png" /></p>

<p>Si posteriormente pulsamos en el boton de &ldquo;OK&rdquo; forzaremos la liberaci&oacute;n. Nosotros recibiremos un mensaje de verificaci&oacute;n de que el t&oacute;tem se ha liberado, mientras que el usuario que retenia el t&oacute;tem recibir&aacute; una notificaci&oacute;n de que el t&oacute;tem ha sido liberado por nosotros.</p>

<p><img alt="" data-rich-file-id="107" src="/system/rich/rich_files/rich_files/000/000/107/original/Imagen17.png" /><img alt="" data-rich-file-id="106" src="/system/rich/rich_files/rich_files/000/000/106/original/Imagen16.png" /></p>

<hr />
<h1><u>NOTIFICACI&Oacute;N DE CIERRE</u></h1>

<p>En caso de que tengamos uno de los t&oacute;tem cogidos y adrede o accidentalmente vayamos a cerrar la aplicaci&oacute;n, recibiremos un mensaje de alerta avis&aacute;ndonos de que tenemos uno de los t&oacute;tems.</p>

<p><img alt="" data-rich-file-id="108" src="/system/rich/rich_files/rich_files/000/000/108/original/Imagen18.png" /></p>

<p>Si pulsamos en &ldquo;No&rdquo;, la aplicaci&oacute;n no se cerrar&aacute;. Si pulsamos en &ldquo;Yes&rdquo; la aplicaci&oacute;n se cerrara y se proceder&aacute; a liberar el t&oacute;tem.</p>

<p>Esto es una medida de seguridad y liberar el t&oacute;tem de esta forma puede llegar a generar problemas, por lo que se recomienda no cerrar nunca la aplicaci&oacute;n de esta forma.</p>

<p>Le deber&iacute;amos dar a &ldquo;No&rdquo;, liberar el t&oacute;tem, y posteriormente cerrar la aplicaci&oacute;n</p>

<hr />
<h1><u>CONTROL DE VERSIONES</u></h1>

<p>Debido a que se trata de una aplicaci&oacute;n en desarrollo, se ha integrado un control de versiones, de modo que si se realizan grandes cambios que afectan a la funcionalidad y compatibilidad entre los distintos clientes o el servidor esto no genere problemas a la hora de utilizarla.</p>

<p>Por ello, se ha habilitado una opci&oacute;n (solo para desarrolladores), que permite bloquear la comunicaci&oacute;n del servidor con los clientes con versiones anteriores. Por tanto, aquellos clientes que tengan una versi&oacute;n antigua, a la hora de lanzar la aplicaci&oacute;n recibir&aacute;n el siguiente mensaje y la aplicaci&oacute;n se cerrar&aacute; sin opci&oacute;n a poder usarse.</p>

<p><img alt="" data-rich-file-id="109" src="/system/rich/rich_files/rich_files/000/000/109/original/Imagen19.png" /></p>

<p>Este mensaje solo aparecer&aacute; si los cambios que se han realizado en la aplicaci&oacute;n hacen que se generen errores con versiones anteriores. Por lo que habr&aacute; ciertos cambios que no requieran de una actualizaci&oacute;n de la aplicaci&oacute;n.</p>

<h1 id="Notas_del_Parche">[[Notas del Parche]]</h1>

<p>Resumen de las diferentes versiones y cambios</p>
