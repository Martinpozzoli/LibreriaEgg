# LibreriaEgg (Proyecto final del cursado)
##  ¿De qué se trata?
        La idea de esta aplicación es armar un sistema completo de gestión en el cual un usuario 
        con el rol USER pueda gestionar sus prestamos de libros y un ADMIN que pueda gestionar 
        los libros disponibles, autores y editoriales.
        
        En la página inicial se usó solo para hacer pruebas con bootstrap. 
        
        Cada usuario deberá registrarse con documento, nombre, apellido, e-mail, número telefónico 
        y contraseña.
        
        Una vez logueado (con email y contraseña) se accede al Home donde se encuentran todos los 
        libros cargados por un ADMIN y la cantidad disponible de cada uno. En la barra de búsqueda
        el usuario puede acceder a libros mediante el título, autor o editorial.
        
        Al acceder a los detalles propios de cada libro se le da al usuario la opción de pedirlos 
        prestados, cada prestamo tiene una duración de 30 días, con un máximo de 3 simultáneos 
        el usuario puede devolverlos en cualquier momento en la sección de Mis Prestamos.
        
## Roles
        El ADMIN tiene acceso a todas las funcionalidades, puede buscar, pedir prestado y acceder 
        al inventario de libros, además puede administrar la carga de libros, editoriales y 
        autores nuevos, y editar los previamente cargados. A diferencia del USER que no puede 
        acceder a la sección de "Administrar DB".
        
## Tecnologías utilizadas
        .JAVA
        .Spring
        .JPA
        .Hibernate
        .MySQL
        .Thymeleaf
