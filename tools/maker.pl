use strict;
use warnings;
use Data::Dumper;
my @colors     = ( 'Color.RED', 'Color.GREEN', 'Color.BLUE', 'Color.YELLOW' );
my $min_width  = 2;
my $max_width  = 478;
my $min_height = 50;
my $max_height = 510;
my @radius     = ( 10, 15, 20, 25, 30, 35, 40, 45, 50 );
my $offset     = 10;
my $item = "\t\tlist.add(new GameLevel.CircleData(#x#, #y#, #r#, #c#));\n";
my $template;
{

    # read whole file's content
    local $/ = undef;
    open TEMPLATE, '<', 'Level.java' or die 'can not open file';
    $template = <TEMPLATE>;
}

#print $template;

for ( my $i = 4 ; $i < 100 ; $i++ ) {
    my @circles = ();
    my $level = sprintf "%02d", $i;
    for ( 0 .. int( rand(10) + 15 ) ) {
        my $circle = {};
        $circle->{r} = get_r();
        $circle->{x} = get_x( $circle->{r} );
        $circle->{y} = get_y( $circle->{r} );
        $circle->{c} = get_c();
        if ( check( $circle, @circles ) ) {
            push @circles, $circle;
        }
        else {
            redo;
        }
    }

    #print Dumper scalar @circles;
    create_file( $level, @circles );
}

sub check {
    my ( $item, @circles ) = @_;
    for my $circle (@circles) {
        my $delta_x = $circle->{x} - $item->{x};
        my $delta_y = $circle->{y} - $item->{y};
        my $delta   = $delta_x**2 + $delta_y**2;
        my $r_sum   = ( $circle->{r} + $item->{r} )**2;
        my $r_minus = ( $circle->{r} - $item->{r} )**2;
        unless ( $delta > $offset**2 + $r_sum
            || $delta < $r_minus - $offset**2 )
        {
            return 0;
        }
    }
    return 1;
}

sub create_file {
    my ( $level, @circles ) = @_;
    my $str;
    for my $circle (@circles) {
        my $tmp_str = $item;
        for my $attr (qw/r x y c/) {
            $tmp_str =~ s/#$attr#/$circle->{$attr}/;
        }
        $str .= $tmp_str;
    }

    #print $str;
    my $tmp_template = $template;
    $tmp_template =~ s/#content#/$str/;
    $tmp_template =~ s/#level#/$level/;
    open OUTPUT, '>', "Level$level.java";
    print OUTPUT $tmp_template;
}

sub get_r {
    my $index = int( rand( scalar @radius ) );
    return $radius[$index];
}

sub get_c {
    my $index = int( rand( scalar @colors ) );
    return $colors[$index];
}

sub get_x {
    my ($r) = @_;
    my $x = int( rand( $max_width - $min_width - 2 * $r ) ) + $min_width + $r;
    return $x;
}

sub get_y {
    my ($r) = @_;
    my $y =
      int( rand( $max_height - $min_height - 2 * $r ) ) + $min_height + $r;
    return $y;
}
