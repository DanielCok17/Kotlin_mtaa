<?php

namespace App\Controller;


use FOS\RestBundle\Controller\AbstractFOSRestController;
use FOS\RestBundle\Controller\Annotations as Rest;

use  App\Repository\BookItemRepository;
use  App\Repository\BookRepository;
use App\Repository\AuthorRepository;
use App\Repository\UserRepository;
use  App\Entity\BookItem;
use  App\Entity\Author;
use  App\Entity\Book;
use  App\Entity\User;





use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\AbstractNormalizer;





use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;


class MainController extends AbstractFOSRestController
{
    /**
     * @return \Symfony\Component\HttpFoundation\JsonResponse
     * @Rest\Route("/library/bookitem", methods={"GET"})
     */
    public function GetAllBookItem(BookItemRepository $BookItemRepository, SerializerInterface $serializer)
    {
        $BookItems =  $BookItemRepository->findAll();
        $view = $this->view($BookItems);
        return $this->handleView($view);

        /*
        $encoder = new JsonEncoder();
        $defaultContext = [
            AbstractNormalizer::CIRCULAR_REFERENCE_HANDLER => function ($BookItems, $format, $context) {
                return $BookItems->getName();
            },
        ];
        $normalizer = new ObjectNormalizer(null, null, null, null, null, null, $defaultContext);

        $serializer = new Serializer([$normalizer], [$encoder]);

        return $this->json( $serializer->serialize($BookItems, 'json'));
        */
    }











    /**
     * @Rest\Route("/library/bookitem/{id}", methods={"GET"})
     */
    public function GetBookItem($id, BookItemRepository $BookItemRepository, SerializerInterface $serializer)
    {

        $view = $this->view($BookItemRepository->find($id));
        return $this->handleView($view);
/*

        return $this->json($BookItemRepository->find($id));
        return $this->json($serializer->serialize($BookItemRepository->find($id), 'json', ['groups' => ['bookitem','book_id' ]]));
        $view = $this->view($BookItemRepository->find($id), 200);

        return $this->handleView($view);
*/
    }


    /**
     * @Rest\Route("/library/bookitem", methods={"POST"})
     */
    public function createBookItem(Request $request, BookRepository $bookrepository)
    {
        $bookItem = new BookItem();
        $bookItem->setState($request->get('state'));
        $bookItem->setShelf($request->get('shelf'));
        $book = $bookrepository->find($request->get('book_id'));
        $bookItem->setBookId($book);
        $bookItem->setCreatedAt();


        $em = $this->getDoctrine()->getManager();
        $em->persist($bookItem);
        $em->flush();

        $view = $this->view($bookItem);
        return $this->handleView($view);
    }

    /**
     * @return \Symfony\Component\HttpFoundation\JsonResponse
     * @Rest\Route("/library/bookitem/{id}", methods={"PUT"})
     */
    public function updateBookItem($id, BookItemRepository $BookItemRepository, Request $request)
    {
        $bookitem = $BookItemRepository->find($id);
        $bookitem->setState($request->get('state'));
        $this->getDoctrine()->getManager()->flush();

        $view = $this->view($bookitem);
        return $this->handleView($view);
    }
    /**
     * @return \Symfony\Component\HttpFoundation\JsonResponse
     * @Rest\Route("/library/bookitem/{id}", methods={"DELETE"})
     */
    public function deleteBookItem($id, BookItemRepository $BookItemRepository)
    {
        $bookitem = $BookItemRepository->find($id);

        $em = $this->getDoctrine()->getManager();
        $em->remove($bookitem);
        $em->flush();

        return $this->json([
            'message' => "Post with ID $id has been deleted"
        ]);
    }

    /**
     * @return \Symfony\Component\HttpFoundation\JsonResponse
     * @Rest\Route("/library/author", methods={"GET"})
     */
    public function GetAllAuthor(AuthorRepository $authorRepository)
    {

        $Authors =  $authorRepository->findAll();
        $view = $this->view($Authors);

        return $this->handleView($view);
    }

    /**
     * @Rest\Route("/library/author", methods={"POST"})
     */
    public function createAuthor(Request $request)
    {
        $author = new Author();
        $author->setName($request->get('name'));

        $author->setCreatedAt();
        $author->setIsDeleted(0);

        $em = $this->getDoctrine()->getManager();
        $em->persist($author);
        $em->flush();

        $view = $this->view($author);
        return $this->handleView($view);
    }

    /**
     * @return \Symfony\Component\HttpFoundation\Response
     * @Rest\Route("/library/book", methods={"GET"})
     */
    public function GetAllBook(BookRepository $BookRepository, SerializerInterface $serializer)
    {
        $book = $BookRepository->findAll();
        $view = $this->view($book);
        return $this->handleView($view);

    }
    /**
     * @Rest\Route("/library/book", methods={"POST"})
     */
    public function createBook(Request $request,AuthorRepository $authorRepository, SluggerInterface $slugger)
    {
        $book = new Book();
        $book->setName($request->get('name'));

        //$destination = $this->getParameter('kernel.project_dir').'/public/files';
        $lol =  $request->files->get('image');
        if($lol){
            $originalFilename = pathinfo($lol->getClientOriginalName(), PATHINFO_FILENAME);
            // this is needed to safely include the file name as part of the URL
            $safeFilename = transliterator_transliterate('Any-Latin; Latin-ASCII; [^A-Za-z0-9_] remove; Lower()', $originalFilename);
            $newFilename = $safeFilename.'-'.uniqid().'.'.$lol->guessExtension();

            // Move the file to the directory where brochures are stored
            try {
                $lol->move(
                    $this->getParameter('kernel.project_dir') . '/public/',
                    $newFilename
                );
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            // updates the 'brochureFilename' property to store the PDF file name
            // instead of its contents
            $book->setImage($newFilename);
        }
        //$book->setImage($request->get('image'));
//        $book->setImage($request->files->get('image'));
        //$myFile = $book->setImage($request->files->get('image'));

        //dd($myFile->move($destination));

        $author = $authorRepository->find($request->get('author_id'));
        $book->setAuthorId($author);
        $book->setCreatedAt();
        $book->setIsDeleted(0);

        $em = $this->getDoctrine()->getManager();
        $em->persist($book);
        $em->flush();

        $view = $this->view($book);
        return $this->handleView($view);
    }
}


