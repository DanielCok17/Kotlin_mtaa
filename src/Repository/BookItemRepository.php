<?php

namespace App\Repository;

use App\Entity\BookItem;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method BookItem|null find($id, $lockMode = null, $lockVersion = null)
 * @method BookItem|null findOneBy(array $criteria, array $orderBy = null)
 * @method BookItem[]    findAll()
 * @method BookItem[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class BookItemRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, BookItem::class);
    }

    // /**
    //  * @return BookItem[] Returns an array of BookItem objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('b')
            ->andWhere('b.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('b.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?BookItem
    {
        return $this->createQueryBuilder('b')
            ->andWhere('b.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
